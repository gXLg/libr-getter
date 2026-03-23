package dev.gxlg.librgetter.mixin.impl;

import dev.gxlg.librgetter.worker.state.StateView;
import dev.gxlg.versiont.gen.net.minecraft.client.Minecraft;
import dev.gxlg.versiont.gen.net.minecraft.client.multiplayer.ClientLevel;
import dev.gxlg.versiont.gen.net.minecraft.client.player.LocalPlayer;
import dev.gxlg.versiont.gen.net.minecraft.core.BlockPos;
import dev.gxlg.versiont.gen.net.minecraft.world.InteractionResult;
import dev.gxlg.versiont.gen.net.minecraft.world.level.block.Blocks;
import dev.gxlg.versiont.gen.net.minecraft.world.phys.BlockHitResult;

import java.util.Optional;

public class MultiPlayerGameModeMixinImpl {
    private final StateView stateView;

    public MultiPlayerGameModeMixinImpl(StateView stateView) {
        this.stateView = stateView;
    }

    public Optional<Boolean> startDestroyBlock(BlockPos blockPos) {
        Minecraft client = Minecraft.getInstance();
        LocalPlayer player = client.getPlayerField();
        ClientLevel world = client.getLevelField();
        if (player == null || world == null) {
            return Optional.empty();
        }
        if (!world.getBlockState(blockPos).is(Blocks.LECTERN())) {
            return Optional.empty();
        }
        if (!stateView.getPermissionManager().allowsBreakingLecterns()) {
            return Optional.of(false);
        }
        return Optional.empty();
    }

    public Optional<InteractionResult> useItemOn(BlockHitResult hitResult) {
        Minecraft client = Minecraft.getInstance();
        LocalPlayer player = client.getPlayerField();
        if (player == null) {
            return Optional.empty();
        }
        BlockPos pos = hitResult.getBlockPos().relative(hitResult.getDirection());
        if (!pos.equals(stateView.getTaskContext().selectedLecternPos())) {
            return Optional.empty();
        }
        if (!stateView.getPermissionManager().allowsPlacingLectern()) {
            return Optional.of(InteractionResult.FAIL());
        }
        return Optional.empty();
    }
}
