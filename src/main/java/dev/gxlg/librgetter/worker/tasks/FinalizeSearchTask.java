package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.reflection.MinecraftHelper;
import dev.gxlg.librgetter.utils.reflection.Support;
import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.types.exceptions.common.InternalErrorException;
import dev.gxlg.librgetter.utils.types.exceptions.tasks.CanNotLockException;
import dev.gxlg.librgetter.utils.types.signals.FinishSignal;
import dev.gxlg.librgetter.utils.types.signals.StopTaskSignal;
import dev.gxlg.librgetter.worker.TaskManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;

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
        LocalPlayer player = client.player;
        if (player == null) {
            throw new InternalErrorException("player");
        }

        if (!Support.isUsingTradeCycling()) {
            // TradeCycling process keeps the screen open, else we have to open it again
            MultiPlayerGameMode manager = client.gameMode;
            if (manager == null) {
                throw new InternalErrorException("manager");
            }

            manager.interact(player, taskContext.selectedVillager(), InteractionHand.MAIN_HAND);
        }

        int buy = canBuy(player, offers.get(0)) ? 0 : (
            canBuy(player, offers.get(1)) ? 1 : -1
        );
        if (buy == -1) {
            throw new CanNotLockException();
        }

        throw new StopTaskSignal(ctx -> TaskManager.TaskSwitch.nextTick(new LockTradesTask(buy), ctx));
    }

    private static boolean canBuy(LocalPlayer player, MerchantOffer offer) {
        ItemStack first = MinecraftHelper.getFirstBuyItem(offer);
        ItemStack second = MinecraftHelper.getSecondBuyItem(offer);
        int firstCount = player.getInventory().countItem(first.getItem());
        int secondCount = second.isEmpty() ? 0 : player.getInventory().countItem(second.getItem());
        return first.getCount() <= firstCount && second.getCount() <= secondCount;
    }
}
