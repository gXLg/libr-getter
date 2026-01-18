package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.types.config.enums.RotationMode;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.common.InternalErrorException;
import dev.gxlg.librgetter.utils.types.signals.StopTaskSignal;
import dev.gxlg.librgetter.worker.TaskManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class RotationTask extends TaskManager.Task {
    private final Vec3 absoluteTarget;

    private final Vec3 relativeTarget;

    private final TaskManager.Task nextTask;

    public RotationTask(LocalPlayer player, Vec3 target, TaskManager.Task nextTask) {
        Vec3 origin = EntityAnchorArgument.Anchor.EYES.apply(player);
        double relativeX = target.x() + (rng.nextFloat() - 0.5F) * 0.4F - origin.x;
        double relativeY = target.y() + (rng.nextFloat() - 0.5F) * 0.4F - origin.y;
        double relativeZ = target.z() + (rng.nextFloat() - 0.5F) * 0.4F - origin.z;

        this.absoluteTarget = target;
        this.relativeTarget = new Vec3(relativeX, relativeY, relativeZ);
        this.nextTask = nextTask;
    }

    @Override
    public void work(TaskManager.TaskContext taskContext) throws StopTaskSignal, LibrGetterException {
        if (LibrGetter.config.manual || LibrGetter.config.rotationMode == RotationMode.NONE) {
            throw new StopTaskSignal(ctx -> TaskManager.TaskSwitch.sameTick(nextTask, ctx));
        }

        Minecraft client = Minecraft.getInstance();
        LocalPlayer player = client.player;
        if (player == null) {
            throw new InternalErrorException("player");
        }

        if (LibrGetter.config.rotationMode == RotationMode.INSTANT) {
            player.lookAt(EntityAnchorArgument.Anchor.EYES, absoluteTarget);
            throw new StopTaskSignal(ctx -> TaskManager.TaskSwitch.sameTick(nextTask, ctx));
        }

        double relativeX = relativeTarget.x();
        double relativeY = relativeTarget.y();
        double relativeZ = relativeTarget.z();
        double distance = Math.hypot(relativeX, relativeZ);
        float goalPitch = Mth.wrapDegrees((float) (-(Mth.atan2(relativeY, distance) * 180.0D / Math.PI)));
        float goalYaw = Mth.wrapDegrees((float) (Mth.atan2(relativeZ, relativeX) * 180.0D / Math.PI) - 90.0F);

        float currentYaw = player.getYRot();
        float currentPitch = player.getXRot();

        float yawDelta = (goalYaw - currentYaw) % 360.0F;
        if (yawDelta < -180.0F) {
            yawDelta += 360.0F;
        }
        if (yawDelta >= 180.0F) {
            yawDelta -= 360.0F;
        }

        float pitchDelta = goalPitch - currentPitch;

        // random lerping
        float newPitch = currentPitch + 0.35F * pitchDelta + (rng.nextFloat() - 0.5F) * 0.2F;
        float newYaw = currentYaw + 0.35F * yawDelta + (rng.nextFloat() - 0.5F) * 0.2F;

        player.setXRot(newPitch);
        player.setYRot(newYaw);
        player.setYHeadRot(player.getYRot());

        if (Math.abs(pitchDelta) < 0.8F && Math.abs(yawDelta) < 0.8F) {
            throw new StopTaskSignal(ctx -> TaskManager.TaskSwitch.sameTick(nextTask, ctx));
        }
    }

    private static final Random rng = new Random();
}
