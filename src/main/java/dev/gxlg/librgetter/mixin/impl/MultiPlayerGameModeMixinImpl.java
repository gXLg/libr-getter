package dev.gxlg.librgetter.mixin.impl;

import dev.gxlg.librgetter.worker.TaskManager;
import dev.gxlg.multiversion.gen.net.minecraft.client.MinecraftWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.ClientLevelWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.client.player.LocalPlayerWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.core.BlockPosWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.InteractionResultWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.level.block.BlocksWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.phys.BlockHitResultWrapper;

import java.util.Optional;

public class MultiPlayerGameModeMixinImpl {

    public static void tick() {
        TaskManager.work();
    }

    public static Optional<Boolean> startDestroyBlock(BlockPosWrapper blockPos) {
        MinecraftWrapper client = MinecraftWrapper.getInstance();
        LocalPlayerWrapper player = client.getPlayerField();
        ClientLevelWrapper world = client.getLevelField();
        if (player == null || world == null) {
            return Optional.empty();
        }
        if (!world.getBlockState(blockPos).is(BlocksWrapper.LECTERN())) {
            return Optional.empty();
        }
        if (!TaskManager.getCurrentTask().allowsBreaking()) {
            return Optional.of(false);
        }
        return Optional.empty();
    }

    public static Optional<InteractionResultWrapper> useItemOn(BlockHitResultWrapper hitResult) {
        MinecraftWrapper client = MinecraftWrapper.getInstance();
        LocalPlayerWrapper player = client.getPlayerField();
        if (player == null) {
            return Optional.empty();
        }
        BlockPosWrapper pos = hitResult.getBlockPos().relative(hitResult.getDirection());
        if (!pos.equals(TaskManager.getTaskContext().selectedLecternPos())) {
            return Optional.empty();
        }
        if (!TaskManager.getCurrentTask().allowsPlacing()) {
            return Optional.of(InteractionResultWrapper.FAIL());
        }
        return Optional.empty();
    }

}
