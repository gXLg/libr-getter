package dev.gxlg.librgetter.utils;

import dev.gxlg.librgetter.utils.chaining.players.Players;
import dev.gxlg.versiont.gen.net.minecraft.client.multiplayer.ClientPacketListener;
import dev.gxlg.versiont.gen.net.minecraft.client.multiplayer.MultiPlayerGameMode;
import dev.gxlg.versiont.gen.net.minecraft.client.player.LocalPlayer;
import dev.gxlg.versiont.gen.net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import dev.gxlg.versiont.gen.net.minecraft.world.entity.player.Inventory;
import dev.gxlg.versiont.gen.net.minecraft.world.inventory.ClickType;

public class InventoryHelper {
    public static void selectItem(LocalPlayer player, int slot, MultiPlayerGameMode game, ClientPacketListener clientNetwork) {
        Inventory inventory = player.getInventory();
        if (!Inventory.isHotbarSlot(slot)) {
            int syncId = player.getInventoryMenuField().getContainerIdField();
            int swap = inventory.getSuitableHotbarSlot();
            game.handleInventoryMouseClick(syncId, slot, swap, ClickType.SWAP(), player);
            slot = swap;
        }
        Players.setSelectedSlot(inventory, slot);
        clientNetwork.send(new ServerboundSetCarriedItemPacket(slot));
    }
}
