package dev.gxlg.librgetter.utils.chaining.players;

import dev.gxlg.versiont.api.V;
import dev.gxlg.versiont.gen.net.minecraft.client.multiplayer.MultiPlayerGameMode;
import dev.gxlg.versiont.gen.net.minecraft.client.player.LocalPlayer;
import dev.gxlg.versiont.gen.net.minecraft.world.entity.player.Inventory;
import dev.gxlg.versiont.gen.net.minecraft.world.phys.BlockHitResult;

public class Players {
    private static final Base implementation;

    static {
        if (V.lower("1.19.0")) {
            implementation = new Players_1_17_0();
        } else if (V.lower("1.21.5")) {
            implementation = new Players_1_19_0();
        } else if (V.equal("1.21.9")) {
            implementation = new Players_1_21_5();
        } else {
            implementation = new Players_1_21_9();
        }
    }

    public static void interactBlock(MultiPlayerGameMode game, LocalPlayer player, BlockHitResult lowBlock, boolean useMainHand) {
        implementation.interactBlock(game, player, lowBlock, useMainHand);
    }

    public static void playFoundNotification(LocalPlayer player) {
        implementation.playFoundNotification(player);
    }

    public static void setSelectedSlot(Inventory inventory, int slot) {
        implementation.setSelectedSlot(inventory, slot);
    }

    public abstract static class Base {
        public abstract void interactBlock(MultiPlayerGameMode game, LocalPlayer player, BlockHitResult lowBlock, boolean useMainHand);

        public abstract void playFoundNotification(LocalPlayer player);

        public abstract void setSelectedSlot(Inventory inventory, int slot);

    }
}
