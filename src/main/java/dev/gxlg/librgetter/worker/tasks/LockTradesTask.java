package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.common.InternalErrorException;
import dev.gxlg.librgetter.utils.types.exceptions.signals.FinishSignal;
import dev.gxlg.librgetter.utils.types.exceptions.signals.StopTaskSignal;
import dev.gxlg.librgetter.worker.TaskManager;
import dev.gxlg.versiont.gen.net.minecraft.client.Minecraft;
import dev.gxlg.versiont.gen.net.minecraft.client.multiplayer.ClientPacketListener;
import dev.gxlg.versiont.gen.net.minecraft.client.multiplayer.MultiPlayerGameMode;
import dev.gxlg.versiont.gen.net.minecraft.client.player.LocalPlayer;
import dev.gxlg.versiont.gen.net.minecraft.network.protocol.game.ServerboundSelectTradePacket;
import dev.gxlg.versiont.gen.net.minecraft.world.inventory.ClickType;

public class LockTradesTask extends TaskManager.Task {
    private final int offerIndex;

    public LockTradesTask(int offer) {
        this.offerIndex = offer;
    }

    @Override
    public void work(TaskManager.TaskContext taskContext) throws StopTaskSignal, LibrGetterException {
        Minecraft client = Minecraft.getInstance();
        LocalPlayer player = client.getPlayerField();
        if (player == null) {
            throw new InternalErrorException("player");
        }
        MultiPlayerGameMode game = client.getGameModeField();
        if (game == null) {
            throw new InternalErrorException("game");
        }

        // select the trade
        if (player.getContainerMenuField().getSlot(0).getContainerField().getItem(0).isEmpty()) {
            ClientPacketListener clientNetwork = client.getConnection();
            if (clientNetwork == null) {
                throw new InternalErrorException("clientNetwork");
            }
            clientNetwork.send(new ServerboundSelectTradePacket(offerIndex));
        }

        // confirm the trade
        game.handleInventoryMouseClick(player.getContainerMenuField().getContainerIdField(), 2, 0, ClickType.PICKUP(), player);
        throw new FinishSignal();
    }
}
