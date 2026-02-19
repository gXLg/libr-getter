package dev.gxlg.librgetter.utils.chaining.players;

import dev.gxlg.versiont.api.V;
import dev.gxlg.versiont.gen.net.minecraft.client.multiplayer.MultiPlayerGameMode;
import dev.gxlg.versiont.gen.net.minecraft.client.player.LocalPlayer;
import dev.gxlg.versiont.gen.net.minecraft.world.entity.player.Inventory;
import dev.gxlg.versiont.gen.net.minecraft.world.phys.BlockHitResult;

public abstract class Players {
    public abstract void interactBlock(MultiPlayerGameMode game, LocalPlayer player, BlockHitResult lowBlock, boolean useMainHand);

    public abstract void playFoundNotification(LocalPlayer player);

    public abstract void setSelectedSlot(Inventory inventory, int slot);

    private static Players implementation = null;

    public static Players getImpl() {
        if (implementation != null) {
            return implementation;
        }
        if (V.lower("1.19.0")) {
            implementation = new Players_1_17_0();
        } else if (V.lower("1.21.5")) {
            implementation = new Players_1_19_0();
        } else if (V.equal("1.21.9")) {
            implementation = new Players_1_21_5();
        } else {
            implementation = new Players_1_21_9();
        }
        return implementation;
    }
}
