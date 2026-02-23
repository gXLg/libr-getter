package dev.gxlg.librgetter.utils.chaining.players;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.versiont.gen.net.minecraft.client.multiplayer.ClientLevel;
import dev.gxlg.versiont.gen.net.minecraft.client.multiplayer.MultiPlayerGameMode;
import dev.gxlg.versiont.gen.net.minecraft.client.player.LocalPlayer;
import dev.gxlg.versiont.gen.net.minecraft.sounds.SoundEvents;
import dev.gxlg.versiont.gen.net.minecraft.sounds.SoundSource;
import dev.gxlg.versiont.gen.net.minecraft.world.InteractionHand;
import dev.gxlg.versiont.gen.net.minecraft.world.entity.player.Inventory;
import dev.gxlg.versiont.gen.net.minecraft.world.phys.BlockHitResult;

public class Players_1_17_0 extends Players.Base {
    protected ClientLevel getWorld(LocalPlayer player) {
        return player.getClientLevelField();
    }

    @Override
    public void interactBlock(MultiPlayerGameMode game, LocalPlayer player, BlockHitResult lowBlock, boolean useMainHand) {
        InteractionHand hand = useMainHand ? InteractionHand.MAIN_HAND() : InteractionHand.OFF_HAND();
        interactBlock(game, player, hand, lowBlock);
    }

    protected void interactBlock(MultiPlayerGameMode game, LocalPlayer player, InteractionHand hand, BlockHitResult lowBlock) {
        game.useItemOn(player, getWorld(player), hand, lowBlock);
    }

    @Override
    public void playFoundNotification(LocalPlayer player) {
        if (!LibrGetter.config.notify) {
            return;
        }
        getWorld(player).playLocalSound(player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_LEVELUP(), SoundSource.NEUTRAL(), 10F, 0.7F, false);
    }

    @Override
    public void setSelectedSlot(Inventory inventory, int slot) {
        inventory.setSelectedField(slot);
    }
}
