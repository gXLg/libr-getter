package dev.gxlg.librgetter.worker.scheduling;

import dev.gxlg.librgetter.worker.scheduling.base.TaskContextUpdateScheduler;
import dev.gxlg.librgetter.worker.scheduling.base.TaskSwitchScheduler;
import dev.gxlg.librgetter.worker.types.context.TaskContext;
import dev.gxlg.librgetter.worker.types.task.Task;

public class SchedulingHandler {
    private final TaskContextUpdateScheduler taskContextUpdateScheduler;

    private final TaskSwitchScheduler taskSwitchScheduler;

    public SchedulingHandler(TaskContextUpdateScheduler taskContextUpdateScheduler, TaskSwitchScheduler taskSwitchScheduler) {
        this.taskContextUpdateScheduler = taskContextUpdateScheduler;
        this.taskSwitchScheduler = taskSwitchScheduler;
    }

    public TaskContext buildTaskContext() {
        return taskContextUpdateScheduler.buildTaskContext();
    }

    public Task takeScheduledTask() {
        return taskSwitchScheduler.takeScheduledTask();
    }

    public boolean isTaskScheduledForSameTick() {
        return taskSwitchScheduler.isTaskScheduledForSameTick();
    }

    public void handleScheduling() {
        taskContextUpdateScheduler.handleScheduling();
        taskSwitchScheduler.handleScheduling();
    }
}
