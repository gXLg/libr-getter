package dev.gxlg.librgetter.utils.chaining.players;

import dev.gxlg.versiont.gen.net.minecraft.client.multiplayer.ClientLevel;
import dev.gxlg.versiont.gen.net.minecraft.client.multiplayer.MultiPlayerGameMode;
import dev.gxlg.versiont.gen.net.minecraft.client.player.LocalPlayer;
import dev.gxlg.versiont.gen.net.minecraft.world.InteractionHand;
import dev.gxlg.versiont.gen.net.minecraft.world.phys.BlockHitResult;

public class Players_1_19_0 extends Players_1_17_0 {
    @Override
    protected ClientLevel getWorld(LocalPlayer player) {
        return (ClientLevel) player.getLevel();
    }

    @Override
    protected void interactBlock(MultiPlayerGameMode game, LocalPlayer player, InteractionHand hand, BlockHitResult lowBlock) {
        game.useItemOn(player, hand, lowBlock);
    }
}
