package dev.gxlg.librgetter.utils.chaining.helper;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.ClientLevelWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.MultiPlayerGameModeWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.client.player.LocalPlayerWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.sounds.SoundEventsWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.sounds.SoundSourceWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.InteractionHandWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.entity.player.InventoryWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.phys.BlockHitResultWrapper;

public class Helper_1_17_0 extends Helper {
    protected ClientLevelWrapper getWorld(LocalPlayerWrapper player) {
        return player.getClientLevelField();
    }

    @Override
    public void interactBlock(MultiPlayerGameModeWrapper game, LocalPlayerWrapper player, BlockHitResultWrapper lowBlock, boolean useMainHand) {
        InteractionHandWrapper hand = useMainHand ? InteractionHandWrapper.MAIN_HAND() : InteractionHandWrapper.OFF_HAND();
        interactBlock(game, player, hand, lowBlock);
    }

    protected void interactBlock(MultiPlayerGameModeWrapper game, LocalPlayerWrapper player, InteractionHandWrapper hand, BlockHitResultWrapper lowBlock) {
        game.useItemOn(player, getWorld(player), hand, lowBlock);
    }

    @Override
    public void playFoundNotification(LocalPlayerWrapper player) {
        if (!LibrGetter.config.notify) {
            return;
        }
        getWorld(player).playLocalSound(player.getX(), player.getY(), player.getZ(), SoundEventsWrapper.PLAYER_LEVELUP(), SoundSourceWrapper.NEUTRAL(), 10F, 0.7F, false);
    }

    @Override
    public void setSelectedSlot(InventoryWrapper inventory, int slot) {
        inventory.setSelectedField(slot);
    }
}
