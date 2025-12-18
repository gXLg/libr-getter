package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.worker.Worker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.network.packet.c2s.play.SelectMerchantTradeC2SPacket;
import net.minecraft.screen.slot.SlotActionType;

public class LockTradesTask extends Worker.Task {
    private final int offerIndex;

    public LockTradesTask(Worker.TaskContext taskContext, int offer) {
        super(taskContext);
        this.offerIndex = offer;
    }

    @Override
    public Worker.TaskSwitch work() {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;
        if (player == null) return internalError("player");
        ClientPlayerInteractionManager manager = client.interactionManager;
        if (manager == null) return internalError("manager");

        // select the trade
        if (player.currentScreenHandler.getSlot(0).inventory.getStack(0).isEmpty()) {
            ClientPlayNetworkHandler handler = client.getNetworkHandler();
            if (handler == null) return internalError("handler");
            handler.sendPacket(new SelectMerchantTradeC2SPacket(offerIndex));
            return noSwitch();
        }

        // confirm the trade
        manager.clickSlot(player.currentScreenHandler.syncId, 2, 0, SlotActionType.PICKUP, player);
        return finish();
    }
}
