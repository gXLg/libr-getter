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

    private final Vec3d absoluteTarget;
    private final Vec3d relativeTarget;
    private final Worker.Task nextTask;

    public RotationTask(Worker.TaskContext taskContext, ClientPlayerEntity player, Vec3d target, Worker.Task nextTask) {
        super(taskContext);

        Vec3d origin = EntityAnchorArgumentType.EntityAnchor.EYES.positionAt(player);
        double relativeX = target.getX() + (rng.nextFloat() - 0.5F) * 0.4F - origin.x;
        double relativeY = target.getY() + (rng.nextFloat() - 0.5F) * 0.4F - origin.y;
        double relativeZ = target.getZ() + (rng.nextFloat() - 0.5F) * 0.4F - origin.z;

        this.absoluteTarget = target;
        this.relativeTarget = new Vec3d(relativeX, relativeY, relativeZ);
        this.nextTask = nextTask;
    }

    @Override
    public Worker.TaskSwitch work() {
        if (LibrGetter.config.manual || LibrGetter.config.rotationMode == RotationMode.NONE) return switchSameTick(nextTask);

        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;
        if (player == null) return internalError("player");

        if (LibrGetter.config.rotationMode == RotationMode.INSTANT) {
            player.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, absoluteTarget);
            return switchSameTick(nextTask);
        }

        double relativeX = relativeTarget.getX();
        double relativeY = relativeTarget.getY();
        double relativeZ = relativeTarget.getZ();
        double distance = Math.hypot(relativeX, relativeZ);
        float goalPitch = MathHelper.wrapDegrees((float) (-(MathHelper.atan2(relativeY, distance) * 180.0D / Math.PI)));
        float goalYaw = MathHelper.wrapDegrees((float) (MathHelper.atan2(relativeZ, relativeX) * 180.0D / Math.PI) - 90.0F);

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
