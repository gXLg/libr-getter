package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.types.config.enums.RotationMode;
import dev.gxlg.librgetter.worker.Worker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

public class RotationTask extends Worker.Task {
    private static final Random rng = new Random();

    private final Vec3d goalRotation;
    private final Vec3d targetPos;
    private final Worker.Task nextTask;

    public RotationTask(Worker.TaskContext taskContext, ClientPlayerEntity player, Vec3d target, Worker.Task nextTask) {
        super(taskContext);

        Vec3d vec3d = EntityAnchorArgumentType.EntityAnchor.EYES.positionAt(player);
        double d = target.getX() + (rng.nextFloat() - 0.5F) * 0.4F - vec3d.x;
        double e = target.getY() + (rng.nextFloat() - 0.5F) * 0.4F - vec3d.y;
        double f = target.getZ() + (rng.nextFloat() - 0.5F) * 0.4F - vec3d.z;

        this.targetPos = target;
        this.goalRotation = new Vec3d(d, e, f);
        this.nextTask = nextTask;
    }

    @Override
    public Worker.TaskSwitch work() {
        if (LibrGetter.config.manual || LibrGetter.config.rotationMode == RotationMode.NONE) return switchSameTick(nextTask);

        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;
        if (player == null) return internalError("player");

        if (LibrGetter.config.rotationMode == RotationMode.INSTANT) {
            player.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, targetPos);
            return switchSameTick(nextTask);
        }

        double d = goalRotation.getX();
        double e = goalRotation.getY();
        double f = goalRotation.getZ();
        double g = Math.sqrt(d * d + f * f);
        float goalPitch = MathHelper.wrapDegrees((float) (-(MathHelper.atan2(e, g) * 180.0D / Math.PI)));
        float goalYaw = MathHelper.wrapDegrees((float) (MathHelper.atan2(f, d) * 180.0D / Math.PI) - 90.0F);

        float currentYaw = player.getYaw();
        float currentPitch = player.getPitch();

        float yawDelta = (goalYaw - currentYaw) % 360.0F;
        if (yawDelta < -180.0F) yawDelta += 360.0F;
        if (yawDelta >= 180.0F) yawDelta -= 360.0F;

        float pitchDelta = goalPitch - currentPitch;

        // random lerping
        float newPitch = currentPitch + 0.35F * pitchDelta + (rng.nextFloat() - 0.5F) * 0.2F;
        float newYaw = currentYaw + 0.35F * yawDelta + (rng.nextFloat() - 0.5F) * 0.2F;

        player.setPitch(newPitch);
        player.setYaw(newYaw);
        player.setHeadYaw(player.getYaw());

        return (Math.abs(pitchDelta) < 0.8F && Math.abs(yawDelta) < 0.8F) ? switchSameTick(nextTask) : noSwitch();
    }
}
