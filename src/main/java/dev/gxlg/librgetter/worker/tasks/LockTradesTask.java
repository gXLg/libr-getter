package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.common.InternalErrorException;
import dev.gxlg.librgetter.utils.types.signals.FinishSignal;
import dev.gxlg.librgetter.utils.types.signals.StopTaskSignal;
import dev.gxlg.librgetter.worker.TaskManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ServerboundSelectTradePacket;
import net.minecraft.world.inventory.ClickType;

public class LockTradesTask extends TaskManager.Task {
    private final int offerIndex;

    public LockTradesTask(int offer) {
        this.offerIndex = offer;
    }

    @Override
    public void work(TaskManager.TaskContext taskContext) throws StopTaskSignal, LibrGetterException {
        Minecraft client = Minecraft.getInstance();
        LocalPlayer player = client.player;
        if (player == null) {
            throw new InternalErrorException("player");
        }
        MultiPlayerGameMode manager = client.gameMode;
        if (manager == null) {
            throw new InternalErrorException("managerInstance");
        }

        // select the trade
        if (player.containerMenu.getSlot(0).container.getItem(0).isEmpty()) {
            ClientPacketListener handler = client.getConnection();
            if (handler == null) {
                throw new InternalErrorException("handler");
            }
            handler.send(new ServerboundSelectTradePacket(offerIndex));
        }

        // confirm the trade
        manager.handleInventoryMouseClick(player.containerMenu.containerId, 2, 0, ClickType.PICKUP, player);
        throw new FinishSignal();
    }
}
