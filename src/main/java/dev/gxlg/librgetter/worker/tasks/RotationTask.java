package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.compatibility.CompatibilityManager;
import dev.gxlg.librgetter.utils.config.Config;
import dev.gxlg.librgetter.utils.config.ConfigManager;
import dev.gxlg.librgetter.utils.types.config.enums.RotationMode;
import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.librgetter.worker.scheduling.controllers.TaskSchedulerController;
import dev.gxlg.librgetter.worker.types.context.MinecraftData;
import dev.gxlg.librgetter.worker.types.context.TaskContext;
import dev.gxlg.librgetter.worker.types.switcher.TaskSwitch;
import dev.gxlg.librgetter.worker.types.task.Task;
import dev.gxlg.versiont.gen.net.minecraft.client.player.LocalPlayer;
import dev.gxlg.versiont.gen.net.minecraft.commands.arguments.EntityAnchorArgument$Anchor;
import dev.gxlg.versiont.gen.net.minecraft.util.Mth;
import dev.gxlg.versiont.gen.net.minecraft.world.phys.Vec3;

import java.util.Random;

public class RotationTask extends Task {
    private final Vec3 absoluteTarget;

    private final Vec3 relativeTarget;

    private final Task nextTask;

    public RotationTask(LocalPlayer player, Vec3 target, Task nextTask) {
        Vec3 origin = EntityAnchorArgument$Anchor.EYES().apply(player);
        double relativeX = target.x() - origin.x() + rngFloatDistributed(ROTATION_GOAL_DEVIATION_RANGE);
        double relativeY = target.y() - origin.y() + rngFloatDistributed(ROTATION_GOAL_DEVIATION_RANGE);
        double relativeZ = target.z() - origin.z() + rngFloatDistributed(ROTATION_GOAL_DEVIATION_RANGE);

        this.absoluteTarget = target;
        this.relativeTarget = new Vec3(relativeX, relativeY, relativeZ);
        this.nextTask = nextTask;
    }

    @Override
    public void work(TaskContext taskContext, TaskSchedulerController controller, ConfigManager configManager, CompatibilityManager compatibilityManager) throws LibrGetterException {
        if (configManager.getBoolean(Config.MANUAL) || configManager.getOptions(Config.ROTATION_MODE) == RotationMode.NONE) {
            controller.scheduleTaskSwitch(TaskSwitch.sameTick(() -> nextTask));
            return;
        }

        MinecraftData minecraftData = taskContext.minecraftData();
        LocalPlayer player = minecraftData.localPlayer;

        if (configManager.getOptions(Config.ROTATION_MODE) == RotationMode.INSTANT) {
            player.lookAt(EntityAnchorArgument$Anchor.EYES(), absoluteTarget);
            controller.scheduleTaskSwitch(TaskSwitch.sameTick(() -> nextTask));
            return;
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
        float newPitch = currentPitch + ROTATION_FACTOR * pitchDelta + rngFloatDistributed(ROTATION_ANGLE_LERPING_DEVIATION_RANGE);
        float newYaw = currentYaw + ROTATION_FACTOR * yawDelta + rngFloatDistributed(ROTATION_ANGLE_LERPING_DEVIATION_RANGE);

        player.setXRot(newPitch);
        player.setYRot(newYaw);
        player.setYHeadRot(player.getYRot());

        if (Math.abs(pitchDelta) < ROTATION_ACCEPTABLE_ANGLE_DELTA && Math.abs(yawDelta) < ROTATION_ACCEPTABLE_ANGLE_DELTA) {
            controller.scheduleTaskSwitch(TaskSwitch.sameTick(() -> nextTask));
        }
    }

    private static final Random rng = new Random();

    private static float rngFloatDistributed(float distributionRange) {
        return (rng.nextFloat() - 0.5F) * distributionRange;
    }
}
