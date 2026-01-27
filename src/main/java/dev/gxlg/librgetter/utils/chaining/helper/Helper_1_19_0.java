package dev.gxlg.librgetter.utils.chaining.helper;

import dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.ClientLevelWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.MultiPlayerGameModeWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.client.player.LocalPlayerWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.InteractionHandWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.phys.BlockHitResultWrapper;

public class Helper_1_19_0 extends Helper_1_17_0 {
    @Override
    protected ClientLevelWrapper getWorld(LocalPlayerWrapper player) {
        return player.getLevel();
    }

    @Override
    protected void interactBlock(MultiPlayerGameModeWrapper game, LocalPlayerWrapper player, InteractionHandWrapper hand, BlockHitResultWrapper lowBlock) {
        game.useItemOn(player, hand, lowBlock);
    }
}
