package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.utils.types.exceptions.tasks.FinishSignal;
import dev.gxlg.librgetter.utils.types.exceptions.tasks.InternalTaskException;
import dev.gxlg.librgetter.utils.types.exceptions.tasks.StopTaskSignal;
import dev.gxlg.librgetter.worker.TaskManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.network.packet.c2s.play.SelectMerchantTradeC2SPacket;
import net.minecraft.screen.slot.SlotActionType;

public class LockTradesTask extends TaskManager.Task {
    private final int offerIndex;

    public LockTradesTask(int offer) {
        this.offerIndex = offer;
    }

    @Override
    public void work(TaskManager.TaskContext taskContext) throws StopTaskSignal {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;
        if (player == null) {
            throw new InternalTaskException("player", this);
        }
        ClientPlayerInteractionManager manager = client.interactionManager;
        if (manager == null) {
            throw new InternalTaskException("manager", this);
        }

        // select the trade
        if (player.currentScreenHandler.getSlot(0).inventory.getStack(0).isEmpty()) {
            ClientPlayNetworkHandler handler = client.getNetworkHandler();
            if (handler == null) {
                throw new InternalTaskException("handler", this);
            }
            handler.sendPacket(new SelectMerchantTradeC2SPacket(offerIndex));
        }

        // confirm the trade
        manager.clickSlot(player.currentScreenHandler.syncId, 2, 0, SlotActionType.PICKUP, player);
        throw new FinishSignal();
    }
}
