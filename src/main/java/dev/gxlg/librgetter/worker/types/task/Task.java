package dev.gxlg.librgetter.worker.types.task;

import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.worker.scheduling.controllers.TaskSchedulerController;
import dev.gxlg.librgetter.worker.types.context.TaskContext;

public abstract class Task {
    public abstract void work(TaskContext taskContext, TaskSchedulerController controller) throws LibrGetterException;

    protected boolean allowsBreakingLecterns() {
        return Permissions.DEFAULT.allowsBreakingLecterns();
    }

    protected boolean allowsPlacingLectern() {
        return Permissions.DEFAULT.allowsPlacingLectern();
    }

    protected boolean allowsSettingTradeOffers() {
        return Permissions.DEFAULT.allowsSettingTradeOffers();
    }

    public Permissions getPermissions() {
        return new Permissions(allowsBreakingLecterns(), allowsPlacingLectern(), allowsSettingTradeOffers());
    }


    protected static final float ROTATION_GOAL_DEVIATION_RANGE = 4.0F;

    protected static final float ROTATION_ANGLE_LERPING_DEVIATION_RANGE = 0.2F;

    protected static final float ROTATION_FACTOR = 0.35F;

    protected static final float ROTATION_ACCEPTABLE_ANGLE_DELTA = 0.8F;

    protected static final float MAX_INTERACTION_DISTANCE = 3.4F;
}
