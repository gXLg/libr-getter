package dev.gxlg.librgetter.worker.tasks.tradehall;

import dev.gxlg.librgetter.compatibility.CompatibilityManager;
import dev.gxlg.librgetter.savefiles.config.ConfigManager;
import dev.gxlg.librgetter.savefiles.goals.GoalListAccessor;
import dev.gxlg.librgetter.savefiles.tradehalls.TradehallAccessor;
import dev.gxlg.librgetter.utils.exceptions.LibrGetterException;
import dev.gxlg.librgetter.worker.scheduling.controllers.TaskSchedulerController;
import dev.gxlg.librgetter.worker.tasks.StartTask;
import dev.gxlg.librgetter.worker.types.context.TaskContext;
import dev.gxlg.librgetter.worker.types.switcher.TaskSwitch;
import dev.gxlg.librgetter.worker.types.task.Task;
import dev.gxlg.versiont.gen.net.minecraft.client.player.LocalPlayer;
import dev.gxlg.versiont.gen.net.minecraft.core.BlockPos;
import dev.gxlg.versiont.gen.net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class WalkTask extends Task {
    private final List<BlockPos> path;

    public WalkTask(List<BlockPos> path) {
        this.path = new ArrayList<>(path);
    }

    @Override
    public void work(TaskContext taskContext, TaskSchedulerController controller, ConfigManager configManager, GoalListAccessor goalListAccessor, TradehallAccessor tradehallAccessor, CompatibilityManager compatibilityManager) throws LibrGetterException {
        LocalPlayer player = taskContext.minecraftData().localPlayer;

        Vec3 playerPos = new Vec3(player.getX(), player.getY(), player.getZ());
        int index = -1;
        double minDistance = Double.POSITIVE_INFINITY;
        for (int i = 0; i < path.size(); i++) {
            BlockPos pos = path.get(i);
            Vec3 posVec = Vec3.atBottomCenterOf(pos);
            double distance = playerPos.distanceTo(posVec);
            if (distance < minDistance) {
                minDistance = distance;
                index = i;
            }
        }
        BlockPos goal = path.get(index);
        Vec3 goalPos = Vec3.atBottomCenterOf(goal);

        double distanceLimit = Double.POSITIVE_INFINITY;
        if (index == path.size() - 1) {
            if (minDistance < 0.01) {
                player.setDeltaMovement(new Vec3(0, 0, 0));
                controller.scheduleTaskSwitch(TaskSwitch.nextTick(StartTask::new));
                return;
            }
            distanceLimit = minDistance;

        } else {
            Vec3 nextPos = Vec3.atBottomCenterOf(path.get(index + 1));
            if (goalPos.distanceTo(nextPos) > playerPos.distanceTo(nextPos) || minDistance < 0.32) {
                goalPos = nextPos;
            }
        }

        // initiate movement
        Vec3 delta = goalPos.subtract(playerPos);
        Vec3 horizontalMove;
        if (delta.x() < 1.0E-8 && delta.z() < 1.0E-8) {
            horizontalMove = new Vec3(0, 0, 0);
        } else {
            horizontalMove = (new Vec3(delta.x(), 0, delta.z())).normalize().scale(Math.min(0.18, distanceLimit));
        }
        boolean jump = goalPos.y() > playerPos.y() && player.onGround();
        Vec3 verticalMove = new Vec3(0, jump ? 0.42 : player.getDeltaMovement().y(), 0);
        player.setDeltaMovement(verticalMove.add(horizontalMove));
    }
}
