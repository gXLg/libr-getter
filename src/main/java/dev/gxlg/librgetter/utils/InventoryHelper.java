package dev.gxlg.librgetter.utils;

import dev.gxlg.librgetter.utils.chaining.helper.Helper;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;

public class InventoryHelper {
    public static void selectItem(LocalPlayer player, int slot, MultiPlayerGameMode manager, ClientPacketListener handler) {
        Inventory inventory = player.getInventory();
        if (!Inventory.isHotbarSlot(slot)) {
            int syncId = player.inventoryMenu.containerId;
            int swap = inventory.getSuitableHotbarSlot();
            manager.handleInventoryMouseClick(syncId, slot, swap, ClickType.SWAP, player);
            slot = swap;
        }
        Helper.getImpl().setSelectedSlot(inventory, slot);
        ServerboundSetCarriedItemPacket packetSelect = new ServerboundSetCarriedItemPacket(slot);
        Helper.getImpl().getConnection(handler).send(packetSelect);
    }
}
