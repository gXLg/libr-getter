package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.command.CommandHelper;
import dev.gxlg.librgetter.utils.reflection.MinecraftHelper;
import dev.gxlg.librgetter.utils.reflection.Parser;
import dev.gxlg.librgetter.utils.reflection.Support;
import dev.gxlg.librgetter.utils.reflection.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.config.enums.MatchMode;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.common.InternalErrorException;
import dev.gxlg.librgetter.utils.types.signals.StopTaskSignal;
import dev.gxlg.librgetter.worker.TaskManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;

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
        LocalPlayer player = client.player;
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

            offeredEnchantments.add(Parser.parseTrade(offers.get(i)));
            if (LibrGetter.config.matchMode == MatchMode.VANILLA) {
                break;
            }
        }
        Texts.getImpl().sendTradeLog(offeredEnchantments);
        Optional<List<EnchantmentTrade>> matching = LibrGetter.config.matchMode.match(offeredEnchantments);
        if (matching.isEmpty()) {
            throw new StopTaskSignal(ctx -> Support.isUsingTradeCycling() ? TaskManager.TaskSwitch.nextTick(new TradeCyclingClickTask(), ctx) :
                                            TaskManager.TaskSwitch.sameTick(new SelectAxeTask(), ctx));
        }

        MinecraftHelper.playFoundNotification(MinecraftHelper.getWorld(player), player);
        matching.get().forEach(e -> Texts.getImpl().sendFound(e, taskContext.attemptsCounter()));

        if (LibrGetter.config.removeGoal) {
            for (EnchantmentTrade e : matching.get()) {
                CommandHelper.removeGoal(e.id(), e.lvl());
            }
        }

        throw new StopTaskSignal(ctx -> TaskManager.TaskSwitch.sameTick(new FinalizeSearchTask(offers), ctx));
    }

    private boolean isEnchantmentTrade(MerchantOffer offer) {
        return offer.getResult().is(Items.ENCHANTED_BOOK) || offer.getResult().is(Items.BOOK);
    }
}
