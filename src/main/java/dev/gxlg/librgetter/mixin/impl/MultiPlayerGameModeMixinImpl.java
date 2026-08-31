package dev.gxlg.librgetter.mixin.impl;

import dev.gxlg.librgetter.worker.state.StateView;
import dev.gxlg.versiont.gen.net.minecraft.client.Minecraft;
import dev.gxlg.versiont.gen.net.minecraft.client.multiplayer.ClientLevel;
import dev.gxlg.versiont.gen.net.minecraft.client.player.LocalPlayer;
import dev.gxlg.versiont.gen.net.minecraft.core.BlockPos;
import dev.gxlg.versiont.gen.net.minecraft.world.InteractionResult;
import dev.gxlg.versiont.gen.net.minecraft.world.entity.Entity;
import dev.gxlg.versiont.gen.net.minecraft.world.entity.npc.villager.Villager;
import dev.gxlg.versiont.gen.net.minecraft.world.level.block.Blocks;
import dev.gxlg.versiont.gen.net.minecraft.world.phys.BlockHitResult;

import java.util.Objects;
import java.util.Optional;

public class MultiPlayerGameModeMixinImpl {
    private final StateView stateView;

    public MultiPlayerGameModeMixinImpl(StateView stateView) {
        this.stateView = stateView;
    }

    public Optional<Boolean> destroyBlock(BlockPos blockPos) {
        Minecraft client = Minecraft.getInstance();
        LocalPlayer player = client.getPlayerField();
        ClientLevel world = client.getLevelField();
        if (player == null || world == null) {
            return Optional.empty();
        }
        if (!world.getBlockState(blockPos).getBlock().equals(Blocks.LECTERN())) {
            return Optional.empty();
        }
        if (stateView.createPermissionView().allowsBreakingLecterns()) {
            return Optional.empty();
        }
        return Optional.of(false);
    }

    public Optional<Object> stopDestroyBlock() {
        if (!stateView.createPermissionView().disablesBlockBreakStopping()) {
            return Optional.empty();
        }
        return Optional.of(new Object());
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
        if (!stateView.createPermissionView().allowsPlacingLectern()) {
            return Optional.of(InteractionResult.FAIL());
        }
        return Optional.empty();
    }

    public Optional<InteractionResult> interact(Entity entity) {
        if (!stateView.createPermissionView().allowsSettingTradeOffers()) {
            return Optional.empty();
        }
        if (!(entity instanceof Villager)) {
            return Optional.empty();
        }
        if (Objects.equals(stateView.getTaskContext().selectedVillager(), entity)) {
            return Optional.empty();
        }
        return Optional.of(InteractionResult.FAIL());
    }
}
