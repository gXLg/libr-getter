package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.utils.types.exceptions.tasks.FinishSignal;
import dev.gxlg.librgetter.utils.types.exceptions.tasks.InternalTaskException;
import dev.gxlg.librgetter.utils.types.exceptions.tasks.StopTaskSignal;
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
    public void work(TaskManager.TaskContext taskContext) throws StopTaskSignal {
        Minecraft client = Minecraft.getInstance();
        LocalPlayer player = client.player;
        if (player == null) {
            throw new InternalTaskException("player", this);
        }
        MultiPlayerGameMode manager = client.gameMode;
        if (manager == null) {
            throw new InternalTaskException("manager", this);
        }

        // select the trade
        if (player.containerMenu.getSlot(0).container.getItem(0).isEmpty()) {
            ClientPacketListener handler = client.getConnection();
            if (handler == null) {
                throw new InternalTaskException("handler", this);
            }
            handler.send(new ServerboundSelectTradePacket(offerIndex));
        }

        // confirm the trade
        manager.handleInventoryMouseClick(player.containerMenu.containerId, 2, 0, ClickType.PICKUP, player);
        throw new FinishSignal();
    }
}
