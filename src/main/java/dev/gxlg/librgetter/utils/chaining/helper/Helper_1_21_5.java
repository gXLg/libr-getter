package dev.gxlg.librgetter.utils.chaining.helper;

import dev.gxlg.multiversion.gen.net.minecraft.world.entity.player.InventoryWrapper;

public class Helper_1_21_5 extends Helper_1_19_0 {
    @Override
    public void setSelectedSlot(InventoryWrapper inventory, int slot) {
        inventory.setSelectedSlot(slot);
    }
}
