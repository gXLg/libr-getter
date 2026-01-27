package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.common.InternalErrorException;
import dev.gxlg.librgetter.utils.types.exceptions.signals.FinishSignal;
import dev.gxlg.librgetter.utils.types.exceptions.signals.StopTaskSignal;
import dev.gxlg.librgetter.worker.TaskManager;
import dev.gxlg.multiversion.gen.net.minecraft.client.MinecraftWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.ClientPacketListenerWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.MultiPlayerGameModeWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.client.player.LocalPlayerWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.network.protocol.game.ServerboundSelectTradePacketWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.inventory.ClickTypeWrapper;

public class LockTradesTask extends TaskManager.Task {
    private final int offerIndex;

    public LockTradesTask(int offer) {
        this.offerIndex = offer;
    }

    @Override
    public void work(TaskManager.TaskContext taskContext) throws StopTaskSignal, LibrGetterException {
        MinecraftWrapper client = MinecraftWrapper.getInstance();
        LocalPlayerWrapper player = client.getPlayerField();
        if (player == null) {
            throw new InternalErrorException("player");
        }
        MultiPlayerGameModeWrapper game = client.getGameModeField();
        if (game == null) {
            throw new InternalErrorException("game");
        }

        // select the trade
        if (player.getContainerMenuField().getSlot(0).getContainerField().getItem(0).isEmpty()) {
            ClientPacketListenerWrapper clientNetwork = client.getConnection();
            if (clientNetwork == null) {
                throw new InternalErrorException("clientNetwork");
            }
            clientNetwork.send(new ServerboundSelectTradePacketWrapper(offerIndex));
        }

        // confirm the trade
        game.handleInventoryMouseClick(player.getContainerMenuField().getContainerIdField(), 2, 0, ClickTypeWrapper.PICKUP(), player);
        throw new FinishSignal();
    }
}
