package dev.gxlg.librgetter.utils.chaining.players;

import dev.gxlg.versiont.gen.net.minecraft.client.multiplayer.MultiPlayerGameMode;
import dev.gxlg.versiont.gen.net.minecraft.client.player.LocalPlayer;
import dev.gxlg.versiont.gen.net.minecraft.world.InteractionHand;
import dev.gxlg.versiont.gen.net.minecraft.world.entity.Entity;
import dev.gxlg.versiont.gen.net.minecraft.world.phys.EntityHitResult;

public class Players_26_1_0 extends Players_1_21_9 {
    @Override
    protected void interactEntity(MultiPlayerGameMode game, LocalPlayer player, Entity entity, InteractionHand hand) {
        game.interact(player, entity, new EntityHitResult(entity), hand);
    }
}
