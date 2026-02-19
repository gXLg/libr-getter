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
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.common.InternalErrorException;
import dev.gxlg.librgetter.utils.types.exceptions.signals.StopTaskSignal;
import dev.gxlg.librgetter.worker.TaskManager;
import dev.gxlg.versiont.gen.net.minecraft.client.Minecraft;
import dev.gxlg.versiont.gen.net.minecraft.client.player.LocalPlayer;
import dev.gxlg.versiont.gen.net.minecraft.world.item.Items;
import dev.gxlg.versiont.gen.net.minecraft.world.item.trading.MerchantOffer;
import dev.gxlg.versiont.gen.net.minecraft.world.item.trading.MerchantOffers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ParseAndMatchTradesTask extends TaskManager.Task {
    private final MerchantOffers offers;

    public ParseAndMatchTradesTask(MerchantOffers trades) {
        this.offers = trades;
    }

    @Override
    public void work(TaskManager.TaskContext taskContext) throws StopTaskSignal, LibrGetterException {
        Minecraft client = Minecraft.getInstance();
        LocalPlayer player = client.getPlayerField();
        if (player == null) {
            throw new InternalErrorException("player");
        }

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
            throw new StopTaskSignal(ctx -> Support.isUsingTradeCycling() ? TaskManager.TaskSwitch.nextTick(new TradeCyclingClickTask(), ctx) :
                                            TaskManager.TaskSwitch.sameTick(new SelectAxeTask(), ctx));
        }

        Players.playFoundNotification(player);
        matching.get().forEach(e -> Texts.sendFound(e, taskContext.attemptsCounter()));

        if (LibrGetter.config.removeGoal) {
            for (EnchantmentTrade trade : matching.get()) {
                CommandHelper.removeGoal(trade);
            }
        }

        throw new StopTaskSignal(ctx -> TaskManager.TaskSwitch.sameTick(new FinalizeSearchTask(offers), ctx));
    }

    private boolean isEnchantmentTrade(MerchantOffer offer) {
        return offer.getResult().is(Items.ENCHANTED_BOOK()) || offer.getResult().is(Items.BOOK());
    }
}
