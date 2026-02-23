package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.command.CommandHelper;
import dev.gxlg.librgetter.utils.chaining.parser.Parser;
import dev.gxlg.librgetter.utils.chaining.players.Players;
import dev.gxlg.librgetter.utils.chaining.support.Support;
import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.config.enums.MatchMode;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
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
    public void work(TaskContext taskContext, TaskSchedulerController controller) throws LibrGetterException {
        List<EnchantmentTrade> offeredEnchantments = new ArrayList<>();
        for (int i = 0; i < offers.size(); i++) {
            if (i >= 2 && LibrGetter.config.matchMode == MatchMode.VANILLA) {
                break;
            }
            MerchantOffer offer = (MerchantOffer) offers.get(i);
            if (!isEnchantmentTrade(offer)) {
                continue;
            }
            EnchantmentTrade trade = Parser.parseTrade(offer);
            if (trade != null) {
                offeredEnchantments.add(trade);
            }
            if (LibrGetter.config.matchMode == MatchMode.VANILLA) {
                break;
            }
        }
        Texts.sendTradeLog(offeredEnchantments);
        Optional<List<EnchantmentTrade>> matching = LibrGetter.config.matchMode.match(offeredEnchantments);
        if (matching.isEmpty()) {
            TaskSwitch taskSwitch;
            if (Support.isUsingTradeCycling()) {
                taskSwitch = TaskSwitch.nextTick(TradeCyclingClickTask::new);
            } else {
                taskSwitch = TaskSwitch.sameTick(SelectAxeTask::new);
            }
            controller.scheduleTaskSwitch(taskSwitch);
            return;
        }

        MinecraftData minecraftData = taskContext.minecraftData();
        Players.playFoundNotification(minecraftData.localPlayer);
        matching.get().forEach(e -> Texts.sendFound(e, taskContext.attemptsCounter()));

        if (LibrGetter.config.removeGoal) {
            for (EnchantmentTrade trade : matching.get()) {
                CommandHelper.removeGoal(trade);
            }
        }

        controller.scheduleTaskSwitch(TaskSwitch.sameTick(() -> new FinalizeSearchTask(offers)));
    }

    private boolean isEnchantmentTrade(MerchantOffer offer) {
        return offer.getResult().is(Items.ENCHANTED_BOOK()) || offer.getResult().is(Items.BOOK());
    }
}
