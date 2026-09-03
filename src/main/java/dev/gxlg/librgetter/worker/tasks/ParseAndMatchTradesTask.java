package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.compatibility.CompatibilityManager;
import dev.gxlg.librgetter.savefiles.config.Config;
import dev.gxlg.librgetter.savefiles.config.ConfigManager;
import dev.gxlg.librgetter.savefiles.config.types.enums.LogMode;
import dev.gxlg.librgetter.savefiles.goals.GoalListAccessor;
import dev.gxlg.librgetter.savefiles.goals.GoalListManager;
import dev.gxlg.librgetter.savefiles.tradehalls.TradehallAccessor;
import dev.gxlg.librgetter.utils.MatchUtil;
import dev.gxlg.librgetter.utils.chaining.players.Players;
import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.messages.translatable.feedback.EnchantmentRemovedMessage;
import dev.gxlg.librgetter.utils.messages.translatable.feedback.OfferMessage;
import dev.gxlg.librgetter.utils.messages.translatable.success.EnchantmentFoundMessage;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.worker.scheduling.controllers.TaskSchedulerController;
import dev.gxlg.librgetter.worker.types.context.MinecraftData;
import dev.gxlg.librgetter.worker.types.context.TaskContext;
import dev.gxlg.librgetter.worker.types.switcher.TaskSwitch;
import dev.gxlg.librgetter.worker.types.task.Task;
import dev.gxlg.versiont.gen.net.minecraft.world.item.trading.MerchantOffer;

import java.util.List;
import java.util.Optional;

public class ParseAndMatchTradesTask extends Task {
    private final List<MerchantOffer> offers;

    public ParseAndMatchTradesTask(List<MerchantOffer> trades) {
        this.offers = trades;
    }

    @Override
    public void work(TaskContext taskContext, TaskSchedulerController controller, ConfigManager configManager, GoalListAccessor goalListAccessor, TradehallAccessor tradehallAccessor, CompatibilityManager compatibilityManager) throws LibrGetterException {
        GoalListManager goalListManager = goalListAccessor.createAccessForCurrentManager();
        List<EnchantmentTrade> offeredEnchantments = MatchUtil.parseTrades(offers, configManager, goalListManager);
        LogMode logMode = configManager.getOptions(Config.LOG_MODE);
        if (logMode != LogMode.NONE) {
            Texts.sendMessage(new OfferMessage(offeredEnchantments), logMode == LogMode.ACTIONBAR);
        }
        Optional<List<EnchantmentTrade>> matching = MatchUtil.matchTrades(offeredEnchantments, configManager, goalListManager);
        if (matching.isEmpty()) {
            TaskSwitch taskSwitch;
            if (compatibilityManager.isUsingTradeCycling()) {
                taskSwitch = TaskSwitch.nextTick(TradeCyclingClickTask::new);
            } else {
                taskSwitch = TaskSwitch.sameTick(SelectAxeTask::new);
            }
            controller.scheduleTaskSwitch(taskSwitch);
            return;
        }
        List<EnchantmentTrade> matchedTrades = matching.get();

        MinecraftData minecraftData = taskContext.minecraftData();
        if (configManager.getBoolean(Config.NOTIFY)) {
            Players.playFoundNotification(minecraftData.localPlayer);
        }
        for (EnchantmentTrade trade : matchedTrades) {
            EnchantmentFoundMessage message = new EnchantmentFoundMessage(trade, taskContext.attemptsCounter(), !configManager.getBoolean(Config.REMOVE_GOAL));
            Texts.sendMessage(message);
        }

        if (configManager.getBoolean(Config.REMOVE_GOAL)) {
            for (EnchantmentTrade trade : matchedTrades) {
                goalListManager.removeMatchingGoal(trade);
                Texts.sendMessage(new EnchantmentRemovedMessage(trade));
            }
            goalListManager.save();
        }

        controller.scheduleTaskSwitch(TaskSwitch.sameTick(() -> new FinalizeSearchTask(offers, matchedTrades)));
    }
}
