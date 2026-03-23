package dev.gxlg.librgetter.worker.types.task;

import dev.gxlg.librgetter.compatibility.CompatibilityManager;
import dev.gxlg.librgetter.utils.config.ConfigManager;
import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.librgetter.worker.scheduling.controllers.TaskSchedulerController;
import dev.gxlg.librgetter.worker.types.context.TaskContext;

public abstract class Task {
    public static final float MAX_INTERACTION_DISTANCE = 3.4F;

    public static final float ROTATION_GOAL_DEVIATION_RANGE = 4.0F;

    public static final float ROTATION_ANGLE_LERPING_DEVIATION_RANGE = 0.2F;

    public static final float ROTATION_FACTOR = 0.35F;

    public static final float ROTATION_ACCEPTABLE_ANGLE_DELTA = 0.8F;

    public abstract void work(TaskContext taskContext, TaskSchedulerController controller, ConfigManager configManager, CompatibilityManager compatibilityManager) throws LibrGetterException;

    protected boolean allowsBreakingLecterns() {
        return PermissionManager.DEFAULT.allowsBreakingLecterns();
    }

    protected boolean allowsPlacingLectern() {
        return PermissionManager.DEFAULT.allowsPlacingLectern();
    }

    protected boolean allowsSettingTradeOffers() {
        return PermissionManager.DEFAULT.allowsSettingTradeOffers();
    }

    public PermissionManager getPermissionManager() {
        return new PermissionManager(allowsBreakingLecterns(), allowsPlacingLectern(), allowsSettingTradeOffers());
    }
}
