package dev.gxlg.librgetter.mixin.impl;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.versiont.gen.net.minecraft.client.Minecraft;
import dev.gxlg.versiont.gen.net.minecraft.client.multiplayer.ClientLevel;
import dev.gxlg.versiont.gen.net.minecraft.client.player.LocalPlayer;
import dev.gxlg.versiont.gen.net.minecraft.core.BlockPos;
import dev.gxlg.versiont.gen.net.minecraft.world.InteractionResult;
import dev.gxlg.versiont.gen.net.minecraft.world.level.block.Blocks;
import dev.gxlg.versiont.gen.net.minecraft.world.phys.BlockHitResult;

import java.util.Optional;

public class MultiPlayerGameModeMixinImpl {

    public static void tick() {
        LibrGetter.worker.work();
    }

    public static Optional<Boolean> startDestroyBlock(BlockPos blockPos) {
        Minecraft client = Minecraft.getInstance();
        LocalPlayer player = client.getPlayerField();
        ClientLevel world = client.getLevelField();
        if (player == null || world == null) {
            return Optional.empty();
        }
        if (!world.getBlockState(blockPos).is(Blocks.LECTERN())) {
            return Optional.empty();
        }
        if (!LibrGetter.worker.getStateView().getPermissionManager().allowsBreakingLecterns()) {
            return Optional.of(false);
        }
        return Optional.empty();
    }

    public static Optional<InteractionResult> useItemOn(BlockHitResult hitResult) {
        Minecraft client = Minecraft.getInstance();
        LocalPlayer player = client.getPlayerField();
        if (player == null) {
            return Optional.empty();
        }
        BlockPos pos = hitResult.getBlockPos().relative(hitResult.getDirection());
        if (!pos.equals(LibrGetter.worker.getStateView().getTaskContext().selectedLecternPos())) {
            return Optional.empty();
        }
        if (!LibrGetter.worker.getStateView().getPermissionManager().allowsPlacingLectern()) {
            return Optional.of(InteractionResult.FAIL());
        }
        return Optional.empty();
    }
}
