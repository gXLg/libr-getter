package dev.gxlg.librgetter.utils.chaining.helper;

import dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.MultiPlayerGameModeWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.client.player.LocalPlayerWrapper;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;

public class Helper_1_19_0 extends Helper_1_17_0 {
    @Override
    protected ClientLevel getWorld(LocalPlayerWrapper player) {
        return player.getLevel();
    }

    @Override
    protected void interactBlock(MultiPlayerGameModeWrapper manager, LocalPlayer player, InteractionHand hand, BlockHitResult lowBlock) {
        manager.useItemOn(player, hand, lowBlock);
    }
}
