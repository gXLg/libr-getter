package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.worker.scheduling.controllers.TaskSchedulerController;
import dev.gxlg.librgetter.worker.types.context.MinecraftData;
import dev.gxlg.librgetter.worker.types.context.TaskContext;
import dev.gxlg.librgetter.worker.types.switcher.TaskSwitch;
import dev.gxlg.librgetter.worker.types.task.Task;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.screens.inventory.MerchantScreen;
import dev.gxlg.versiont.gen.net.minecraft.client.player.LocalPlayer;
import dev.gxlg.versiont.gen.net.minecraft.network.protocol.game.ServerboundSelectTradePacket;
import dev.gxlg.versiont.gen.net.minecraft.world.inventory.ClickType;

public class LockTradesTask extends Task {
    private final int offerIndex;

    public LockTradesTask(int offer) {
        this.offerIndex = offer;
    }

    @Override
    public void work(TaskContext taskContext, TaskSchedulerController controller) throws LibrGetterException {
        MinecraftData minecraftData = taskContext.minecraftData();

        // wait for the screen to open
        if (!(minecraftData.client.getScreenField() instanceof MerchantScreen)) {
            return;
        }

        LocalPlayer player = minecraftData.localPlayer;

        // wait for the first slot to be updated server-side
        if (player.getContainerMenuField().getSlot(0).getContainerField().getItem(0).isEmpty()) {
            // select the trade
            minecraftData.clientNetwork.send(new ServerboundSelectTradePacket(offerIndex));
            return;
        }
        // confirm the trade
        minecraftData.gameMode.handleInventoryMouseClick(player.getContainerMenuField().getContainerIdField(), 2, 0, ClickType.PICKUP(), player);

        controller.scheduleTaskSwitch(TaskSwitch.nextTick(StandbyTask::new));
    }
}
