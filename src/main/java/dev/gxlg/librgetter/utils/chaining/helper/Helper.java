package dev.gxlg.librgetter.utils.chaining.helper;

import dev.gxlg.multiversion.V;
import dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.MultiPlayerGameModeWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.client.player.LocalPlayerWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.entity.player.InventoryWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.phys.BlockHitResultWrapper;

public abstract class Helper {
    public abstract void interactBlock(MultiPlayerGameModeWrapper game, LocalPlayerWrapper player, BlockHitResultWrapper lowBlock, boolean useMainHand);

    public abstract void playFoundNotification(LocalPlayerWrapper player);

    public abstract void setSelectedSlot(InventoryWrapper inventory, int slot);

    private static Helper implementation = null;

    public static Helper getImpl() {
        if (implementation != null) {
            return implementation;
        }
        if (V.lower("1.19.0")) {
            implementation = new Helper_1_17_0();
        } else if (V.lower("1.21.5")) {
            implementation = new Helper_1_19_0();
        } else if (V.equal("1.21.9")) {
            implementation = new Helper_1_21_5();
        } else {
            implementation = new Helper_1_21_9();
        }
        return implementation;
    }
}
