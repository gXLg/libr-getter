package dev.gxlg.librgetter.particles;

import dev.gxlg.librgetter.utils.TickUtil;
import dev.gxlg.librgetter.utils.chaining.world.World;
import dev.gxlg.librgetter.worker.state.StateView;
import dev.gxlg.librgetter.worker.types.context.TaskContext;
import dev.gxlg.versiont.gen.net.minecraft.client.Minecraft;
import dev.gxlg.versiont.gen.net.minecraft.client.multiplayer.ClientLevel;
import dev.gxlg.versiont.gen.net.minecraft.client.player.LocalPlayer;
import dev.gxlg.versiont.gen.net.minecraft.commands.arguments.EntityAnchorArgument$Anchor;
import dev.gxlg.versiont.gen.net.minecraft.core.BlockPos;
import dev.gxlg.versiont.gen.net.minecraft.world.entity.npc.villager.Villager;
import dev.gxlg.versiont.gen.net.minecraft.world.entity.player.Player;
import dev.gxlg.versiont.gen.net.minecraft.world.phys.Vec3;

public class WorkstationParticleSpawner {
    public static final int PARTICLE_R = 255;

    public static final int PARTICLE_G = 0;

    public static final int PARTICLE_B = 255;

    public static final float PARTICLE_SCALE = 2.0F;

    public static final int SCAN_RADIUS = 64;

    private final StateView stateView;

    public WorkstationParticleSpawner(StateView stateView) {
        this.stateView = stateView;
    }

    public void start() {
        TickUtil.registerLevelTicker(this::tick);
    }

    private void tick(ClientLevel level) {
        LocalPlayer player = Minecraft.getInstance().getPlayerField();
        if (player == null) {
            return;
        }
        TaskContext ctx = stateView.getTaskContext();
        Villager villager = ctx.selectedVillager();
        if (villager != null) {
            checkVillager(level, player, villager);
        }
        BlockPos lectern = ctx.selectedLecternPos();
        if (lectern != null) {
            checkLectern(level, player, lectern);
        }
    }

    private void checkVillager(ClientLevel level, Player player, Villager villager) {
        if (player.distanceTo(villager) > SCAN_RADIUS) {
            return;
        }
        Vec3 eyes = EntityAnchorArgument$Anchor.EYES().apply(villager);
        Vec3 direction = player.getPositionField().subtract(eyes).normalize();
        Vec3 pos = eyes.add(direction.scale(0.5D));
        spawnParticle(level, pos);
    }

    private void checkLectern(ClientLevel level, Player player, BlockPos lecternPos) {
        if (!player.blockPosition().closerThan(lecternPos, SCAN_RADIUS)) {
            return;
        }
        Vec3 center = Vec3.atBottomCenterOf(lecternPos).add(new Vec3(0.0D, 0.75D, 0.0D));
        Vec3 direction = player.getPositionField().subtract(center).normalize();
        Vec3 pos = center.add(direction.scale(0.5D));
        spawnParticle(level, pos);
    }

    private void spawnParticle(ClientLevel level, Vec3 pos) {
        level.addParticle(World.createDustParticle(PARTICLE_R, PARTICLE_G, PARTICLE_B, PARTICLE_SCALE), pos.x(), pos.y(), pos.z(), 0.0F, 0.0F, 0.0F);
    }
}
