package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.command.CommandHelper;
import dev.gxlg.librgetter.utils.chaining.helper.Helper;
import dev.gxlg.librgetter.utils.chaining.parser.Parser;
import dev.gxlg.librgetter.utils.chaining.support.Support;
import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.config.enums.MatchMode;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.common.InternalErrorException;
import dev.gxlg.librgetter.utils.types.exceptions.signals.StopTaskSignal;
import dev.gxlg.librgetter.worker.TaskManager;
import dev.gxlg.multiversion.gen.net.minecraft.client.MinecraftWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.client.player.LocalPlayerWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemsWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.item.trading.MerchantOfferWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ParseAndMatchTradesTask extends TaskManager.Task {
    private final List<MerchantOfferWrapper> offers;

    public ParseAndMatchTradesTask(List<MerchantOfferWrapper> trades) {
        this.offers = trades;
    }

    @Override
    public void work(TaskManager.TaskContext taskContext) throws StopTaskSignal, LibrGetterException {
        MinecraftWrapper client = MinecraftWrapper.getInstance();
        LocalPlayerWrapper player = client.getPlayerField();
        if (player == null) {
            throw new InternalErrorException("player");
        }

        List<EnchantmentTrade> offeredEnchantments = new ArrayList<>();
        for (int i = 0; i < offers.size(); i++) {
            if (i >= 2 && LibrGetter.config.matchMode == MatchMode.VANILLA) {
                break;
            }
            if (!isEnchantmentTrade(offers.get(i))) {
                continue;
            }
            EnchantmentTrade trade = Parser.getImpl().parseTrade(offers.get(i));
            if (trade != null) {
                offeredEnchantments.add(trade);
            }
            if (LibrGetter.config.matchMode == MatchMode.VANILLA) {
                break;
            }
        }
        Texts.getImpl().sendTradeLog(offeredEnchantments);
        Optional<List<EnchantmentTrade>> matching = LibrGetter.config.matchMode.match(offeredEnchantments);
        if (matching.isEmpty()) {
            throw new StopTaskSignal(ctx -> Support.getImpl().isUsingTradeCycling() ? TaskManager.TaskSwitch.nextTick(new TradeCyclingClickTask(), ctx) :
                                            TaskManager.TaskSwitch.sameTick(new SelectAxeTask(), ctx));
        }

        Helper.getImpl().playFoundNotification(player);
        matching.get().forEach(e -> Texts.getImpl().sendFound(e, taskContext.attemptsCounter()));

        if (LibrGetter.config.removeGoal) {
            for (EnchantmentTrade trade : matching.get()) {
                CommandHelper.removeGoal(trade);
            }
        }

        throw new StopTaskSignal(ctx -> TaskManager.TaskSwitch.sameTick(new FinalizeSearchTask(offers), ctx));
    }

    private boolean isEnchantmentTrade(MerchantOfferWrapper offer) {
        return offer.getResult().is(ItemsWrapper.ENCHANTED_BOOK()) || offer.getResult().is(ItemsWrapper.BOOK());
    }
}
