package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.chaining.support.Support;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.common.InternalErrorException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.tasks.CanNotLockException;
import dev.gxlg.librgetter.utils.types.exceptions.signals.FinishSignal;
import dev.gxlg.librgetter.utils.types.exceptions.signals.StopTaskSignal;
import dev.gxlg.librgetter.worker.TaskManager;
import dev.gxlg.versiont.gen.net.minecraft.client.Minecraft;
import dev.gxlg.versiont.gen.net.minecraft.client.multiplayer.MultiPlayerGameMode;
import dev.gxlg.versiont.gen.net.minecraft.client.player.LocalPlayer;
import dev.gxlg.versiont.gen.net.minecraft.world.InteractionHand;
import dev.gxlg.versiont.gen.net.minecraft.world.item.ItemStack;
import dev.gxlg.versiont.gen.net.minecraft.world.item.trading.MerchantOffer;
import dev.gxlg.versiont.gen.net.minecraft.world.item.trading.MerchantOffers;

public class FinalizeSearchTask extends TaskManager.Task {
    private final MerchantOffers offers;

    public FinalizeSearchTask(MerchantOffers offers) {
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

        Minecraft client = Minecraft.getInstance();
        LocalPlayer player = client.getPlayerField();
        if (player == null) {
            throw new InternalErrorException("player");
        }

        if (!Support.isUsingTradeCycling()) {
            // TradeCycling process keeps the screen open, else we have to open it again
            MultiPlayerGameMode game = client.getGameModeField();
            if (game == null) {
                throw new InternalErrorException("game");
            }
            game.interact(player, taskContext.selectedVillager(), InteractionHand.MAIN_HAND());
        }

        int buy = canBuy(player, (MerchantOffer) offers.get(0)) ? 0 : (
            canBuy(player, (MerchantOffer) offers.get(1)) ? 1 : -1
        );
        if (buy == -1) {
            throw new CanNotLockException();
        }

        throw new StopTaskSignal(ctx -> TaskManager.TaskSwitch.nextTick(new LockTradesTask(buy), ctx));
    }

    private static boolean canBuy(LocalPlayer player, MerchantOffer offer) {
        ItemStack first = offer.getCostA();
        ItemStack second = offer.getCostB();
        int firstCount = player.getInventory().countItem(first.getItem());
        int secondCount = second.isEmpty() ? 0 : player.getInventory().countItem(second.getItem());
        return first.getCount() <= firstCount && second.getCount() <= secondCount;
    }
}
