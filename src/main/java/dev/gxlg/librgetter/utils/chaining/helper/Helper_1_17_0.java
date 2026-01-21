package dev.gxlg.librgetter.utils.chaining.helper;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.ClientPacketListenerWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.MultiPlayerGameModeWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.client.player.LocalPlayerWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.InteractionResultWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.entity.player.InventoryWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.level.LevelWrapper;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.Connection;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class Helper_1_17_0 extends Helper {

    @Override
    public Connection getConnection(ClientPacketListener handler) {
        return ClientPacketListenerWrapper.inst(handler).getConnection();
    }

    @Override
    public ClientLevel getWorld(LocalPlayer player) {
        return getWorld(LocalPlayerWrapper.inst(player));
    }

    protected ClientLevel getWorld(LocalPlayerWrapper player) {
        return player.getClientLevel();
    }

    @Override
    public void interactBlock(MultiPlayerGameMode manager, LocalPlayer player, BlockHitResult lowBlock, boolean useMainHand) {
        InteractionHand hand = useMainHand ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
        MultiPlayerGameModeWrapper managerWrapper = MultiPlayerGameModeWrapper.inst(manager);
        interactBlock(managerWrapper, player, hand, lowBlock);
    }

    protected void interactBlock(MultiPlayerGameModeWrapper manager, LocalPlayer player, InteractionHand hand, BlockHitResult lowBlock) {
        manager.useItemOn(player, getWorld(player), hand, lowBlock);
    }

    @Override
    public void playFoundNotification(LocalPlayer player) {
        if (!LibrGetter.config.notify) {
            return;
        }
        LevelWrapper.inst(getWorld(player)).playLocalSound(player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_LEVELUP, SoundSource.NEUTRAL, 10F, 0.7F, false);
    }

    @Override
    public void setActionResultFail(CallbackInfoReturnable<InteractionResult> info) {
        info.setReturnValue(InteractionResultWrapper.FAIL());
    }

    @Override
    public void setSelectedSlot(Inventory inventory, int slot) {
        setSelectedSlot(InventoryWrapper.inst(inventory), slot);
    }

    protected void setSelectedSlot(InventoryWrapper inventory, int slot) {
        inventory.setSelected(slot);
    }
}
