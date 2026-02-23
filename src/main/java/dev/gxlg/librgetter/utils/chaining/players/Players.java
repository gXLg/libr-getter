package dev.gxlg.librgetter.utils.chaining.players;

import dev.gxlg.versiont.api.V;
import dev.gxlg.versiont.gen.net.minecraft.client.multiplayer.MultiPlayerGameMode;
import dev.gxlg.versiont.gen.net.minecraft.client.player.LocalPlayer;
import dev.gxlg.versiont.gen.net.minecraft.world.entity.player.Inventory;
import dev.gxlg.versiont.gen.net.minecraft.world.phys.BlockHitResult;

public class Players {
    private static Base implementation = null;

    public static void interactBlock(MultiPlayerGameMode game, LocalPlayer player, BlockHitResult lowBlock, boolean useMainHand) {
        getImpl().interactBlock(game, player, lowBlock, useMainHand);
    }

    public static void playFoundNotification(LocalPlayer player) {
        getImpl().playFoundNotification(player);
    }

    public static void setSelectedSlot(Inventory inventory, int slot) {
        getImpl().setSelectedSlot(inventory, slot);
    }

    private static Base getImpl() {
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

    public abstract static class Base {
        public abstract void interactBlock(MultiPlayerGameMode game, LocalPlayer player, BlockHitResult lowBlock, boolean useMainHand);

        public abstract void playFoundNotification(LocalPlayer player);

        public abstract void setSelectedSlot(Inventory inventory, int slot);

    }
}
