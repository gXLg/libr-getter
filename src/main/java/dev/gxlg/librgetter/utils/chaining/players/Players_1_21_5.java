package dev.gxlg.librgetter.utils.chaining.players;

import dev.gxlg.versiont.gen.net.minecraft.world.entity.player.Inventory;

public class Players_1_21_5 extends Players_1_19_0 {
    @Override
    public void setSelectedSlot(Inventory inventory, int slot) {
        inventory.setSelectedSlot(slot);
    }
}
