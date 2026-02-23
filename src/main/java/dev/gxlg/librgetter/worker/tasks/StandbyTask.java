package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.worker.scheduling.controllers.TaskSchedulerController;
import dev.gxlg.librgetter.worker.types.context.TaskContext;
import dev.gxlg.librgetter.worker.types.task.Task;

public class StandbyTask extends Task {
    @Override
    public void work(TaskContext taskContext, TaskSchedulerController controller) {
    }

    @Override
    protected boolean allowsBreakingLecterns() {
        return true;
    }

    @Override
    protected boolean allowsPlacingLectern() {
        return true;
    }
}
