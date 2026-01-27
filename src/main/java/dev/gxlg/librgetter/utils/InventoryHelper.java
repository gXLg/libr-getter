package dev.gxlg.librgetter.utils;

import dev.gxlg.librgetter.utils.chaining.helper.Helper;
import dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.ClientPacketListenerWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.MultiPlayerGameModeWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.client.player.LocalPlayerWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacketWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.entity.player.InventoryWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.inventory.ClickTypeWrapper;

public class InventoryHelper {
    public static void selectItem(LocalPlayerWrapper player, int slot, MultiPlayerGameModeWrapper game, ClientPacketListenerWrapper clientNetwork) {
        InventoryWrapper inventory = player.getInventory();
        if (!InventoryWrapper.isHotbarSlot(slot)) {
            int syncId = player.getInventoryMenuField().getContainerIdField();
            int swap = inventory.getSuitableHotbarSlot();
            game.handleInventoryMouseClick(syncId, slot, swap, ClickTypeWrapper.SWAP(), player);
            slot = swap;
        }
        Helper.getImpl().setSelectedSlot(inventory, slot);
        clientNetwork.send(new ServerboundSetCarriedItemPacketWrapper(slot));
    }
}
