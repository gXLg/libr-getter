package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.chaining.support.Support;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.common.InternalErrorException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.tasks.CanNotLockException;
import dev.gxlg.librgetter.utils.types.exceptions.signals.FinishSignal;
import dev.gxlg.librgetter.utils.types.exceptions.signals.StopTaskSignal;
import dev.gxlg.librgetter.worker.TaskManager;
import dev.gxlg.multiversion.gen.net.minecraft.client.MinecraftWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.MultiPlayerGameModeWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.client.player.LocalPlayerWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.InteractionHandWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemStackWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.item.trading.MerchantOfferWrapper;

import java.util.List;

public class FinalizeSearchTask extends TaskManager.Task {
    private final List<MerchantOfferWrapper> offers;

    public FinalizeSearchTask(List<MerchantOfferWrapper> offers) {
        this.offers = offers;
    }

    @Override
    public void work(TaskManager.TaskContext taskContext) throws StopTaskSignal, LibrGetterException {
        if (LibrGetter.config.manual) {
            throw new StopTaskSignal(ctx -> TaskManager.TaskSwitch.sameTick(new ManualModeWaitTask(), ctx));
        }

        if (!LibrGetter.config.lock) {
            throw new FinishSignal();
        }

        MinecraftWrapper client = MinecraftWrapper.getInstance();
        LocalPlayerWrapper player = client.getPlayerField();
        if (player == null) {
            throw new InternalErrorException("player");
        }

        if (!Support.getImpl().isUsingTradeCycling()) {
            // TradeCycling process keeps the screen open, else we have to open it again
            MultiPlayerGameModeWrapper game = client.getGameModeField();
            if (game == null) {
                throw new InternalErrorException("game");
            }
            game.interact(player, taskContext.selectedVillager(), InteractionHandWrapper.MAIN_HAND());
        }

        int buy = canBuy(player, offers.get(0)) ? 0 : (
            canBuy(player, offers.get(1)) ? 1 : -1
        );
        if (buy == -1) {
            throw new CanNotLockException();
        }

        throw new StopTaskSignal(ctx -> TaskManager.TaskSwitch.nextTick(new LockTradesTask(buy), ctx));
    }

    private static boolean canBuy(LocalPlayerWrapper player, MerchantOfferWrapper offer) {
        ItemStackWrapper first = offer.getCostA();
        ItemStackWrapper second = offer.getCostB();
        int firstCount = player.getInventory().countItem(first.getItem());
        int secondCount = second.isEmpty() ? 0 : player.getInventory().countItem(second.getItem());
        return first.getCount() <= firstCount && second.getCount() <= secondCount;
    }
}
