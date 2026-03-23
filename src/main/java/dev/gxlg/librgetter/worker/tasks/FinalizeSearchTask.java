package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.compatibility.CompatibilityManager;
import dev.gxlg.librgetter.utils.config.Config;
import dev.gxlg.librgetter.utils.config.ConfigManager;
import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.types.exceptions.tasks.CanNotLockException;
import dev.gxlg.librgetter.worker.scheduling.controllers.TaskSchedulerController;
import dev.gxlg.librgetter.worker.types.context.MinecraftData;
import dev.gxlg.librgetter.worker.types.context.TaskContext;
import dev.gxlg.librgetter.worker.types.switcher.TaskSwitch;
import dev.gxlg.librgetter.worker.types.task.Task;
import dev.gxlg.versiont.gen.net.minecraft.world.InteractionHand;
import dev.gxlg.versiont.gen.net.minecraft.world.entity.player.Inventory;
import dev.gxlg.versiont.gen.net.minecraft.world.item.ItemStack;
import dev.gxlg.versiont.gen.net.minecraft.world.item.trading.MerchantOffer;
import dev.gxlg.versiont.gen.net.minecraft.world.item.trading.MerchantOffers;

public class FinalizeSearchTask extends Task {
    private final MerchantOffers offers;

    public FinalizeSearchTask(MerchantOffers offers) {
        this.offers = offers;
    }

    @Override
    public void work(TaskContext taskContext, TaskSchedulerController controller, ConfigManager configManager, CompatibilityManager compatibilityManager) throws LibrGetterException {
        if (configManager.getBoolean(Config.MANUAL)) {
            controller.scheduleTaskSwitch(TaskSwitch.sameTick(ManualModeWaitTask::new));
            return;
        }

        if (!configManager.getBoolean(Config.LOCK)) {
            controller.scheduleTaskSwitch(TaskSwitch.nextTick(StandbyTask::new));
            return;
        }

        MinecraftData minecraftData = taskContext.minecraftData();
        if (!compatibilityManager.isUsingTradeCycling()) {
            // TradeCycling process keeps the screen open, else we have to open it again
            minecraftData.gameMode.interact(minecraftData.localPlayer, taskContext.selectedVillager(), InteractionHand.MAIN_HAND());
        }

        Inventory inventory = minecraftData.localPlayer.getInventory();
        int buy;
        if (canBuy(inventory, (MerchantOffer) offers.get(0))) {
            buy = 0;
        } else if (canBuy(inventory, (MerchantOffer) offers.get(1))) {
            buy = 1;
        } else {
            throw new CanNotLockException();
        }

        controller.scheduleTaskSwitch(TaskSwitch.nextTick(() -> new LockTradesTask(buy)));
    }

    private static boolean canBuy(Inventory inventory, MerchantOffer offer) {
        ItemStack first = offer.getCostA();
        ItemStack second = offer.getCostB();
        int firstCount = inventory.countItem(first.getItem());
        int secondCount = second.isEmpty() ? 0 : inventory.countItem(second.getItem());
        return first.getCount() <= firstCount && second.getCount() <= secondCount;
    }
}
