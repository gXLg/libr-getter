package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.compatibility.CompatibilityManager;
import dev.gxlg.librgetter.utils.chaining.parser.Parser;
import dev.gxlg.librgetter.utils.chaining.players.Players;
import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.config.Config;
import dev.gxlg.librgetter.utils.config.ConfigManager;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.config.enums.LogMode;
import dev.gxlg.librgetter.utils.types.config.enums.MatchMode;
import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.types.messages.translatable.feedback.EnchantmentRemovedMessage;
import dev.gxlg.librgetter.utils.types.messages.translatable.feedback.OfferMessage;
import dev.gxlg.librgetter.utils.types.messages.translatable.success.EnchantmentFoundMessage;
import dev.gxlg.librgetter.worker.scheduling.controllers.TaskSchedulerController;
import dev.gxlg.librgetter.worker.types.context.MinecraftData;
import dev.gxlg.librgetter.worker.types.context.TaskContext;
import dev.gxlg.librgetter.worker.types.switcher.TaskSwitch;
import dev.gxlg.librgetter.worker.types.task.Task;
import dev.gxlg.versiont.gen.net.minecraft.world.item.Items;
import dev.gxlg.versiont.gen.net.minecraft.world.item.trading.MerchantOffer;
import dev.gxlg.versiont.gen.net.minecraft.world.item.trading.MerchantOffers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ParseAndMatchTradesTask extends Task {
    private final MerchantOffers offers;

    public ParseAndMatchTradesTask(MerchantOffers trades) {
        this.offers = trades;
    }

    @Override
    public void work(TaskContext taskContext, TaskSchedulerController controller, ConfigManager configManager, CompatibilityManager compatibilityManager) throws LibrGetterException {
        List<EnchantmentTrade> offeredEnchantments = new ArrayList<>();
        for (int i = 0; i < offers.size(); i++) {
            if (i >= 2 && configManager.getOptions(Config.MATCH_MODE) == MatchMode.VANILLA) {
                break;
            }
            MerchantOffer offer = (MerchantOffer) offers.get(i);
            if (!isEnchantmentTrade(offer)) {
                continue;
            }
            EnchantmentTrade trade = Parser.parseTrade(offer, configManager);
            if (trade != null) {
                offeredEnchantments.add(trade);
            }
            if (configManager.getOptions(Config.MATCH_MODE) == MatchMode.VANILLA) {
                break;
            }
        }
        LogMode logMode = configManager.getOptions(Config.LOG_MODE);
        if (logMode != LogMode.NONE) {
            Texts.sendMessage(new OfferMessage(offeredEnchantments), logMode == LogMode.ACTIONBAR);
        }
        Optional<List<EnchantmentTrade>> matching = configManager.<MatchMode>getOptions(Config.MATCH_MODE).match(offeredEnchantments, configManager);
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

        MinecraftData minecraftData = taskContext.minecraftData();
        if (configManager.getBoolean(Config.NOTIFY)) {
            Players.playFoundNotification(minecraftData.localPlayer);
        }
        matching.get().forEach(e -> {
            EnchantmentFoundMessage message = new EnchantmentFoundMessage(e, taskContext.attemptsCounter(), !configManager.getBoolean(Config.REMOVE_GOAL));
            Texts.sendMessage(message);
        });

        if (configManager.getBoolean(Config.REMOVE_GOAL)) {
            for (EnchantmentTrade trade : matching.get()) {
                configManager.getData().removeGoal(trade);
                Texts.sendMessage(new EnchantmentRemovedMessage(trade));
            }
            configManager.save();
        }

        controller.scheduleTaskSwitch(TaskSwitch.sameTick(() -> new FinalizeSearchTask(offers)));
    }

    private boolean isEnchantmentTrade(MerchantOffer offer) {
        return offer.getResult().is(Items.ENCHANTED_BOOK()) || offer.getResult().is(Items.BOOK());
    }
}
