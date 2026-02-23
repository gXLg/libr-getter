package dev.gxlg.librgetter.worker.scheduling.base;

import dev.gxlg.librgetter.worker.types.scheduling.AbstractScheduler;
import dev.gxlg.librgetter.worker.types.switcher.TaskSwitch;
import dev.gxlg.librgetter.worker.types.task.Task;

public class TaskSwitchScheduler extends AbstractScheduler<TaskSwitch> {
    private TaskSwitch scheduledTaskSwitch;

    private Task lastTakenTask;

    public TaskSwitchScheduler(Task firstTask) {
        this.scheduledTaskSwitch = TaskSwitch.nextTick(() -> firstTask);
    }

    public Task takeScheduledTask() {
        lastTakenTask = scheduledTaskSwitch.taskSupplier().get();
        return lastTakenTask;
    }

    public boolean isTaskScheduledForSameTick() {
        return scheduledTaskSwitch.isSameTick();
    }

    @Override
    protected void handleEmptyScheduling() {
        scheduledTaskSwitch = TaskSwitch.nextTick(() -> lastTakenTask);
    }

    @Override
    protected void handleScheduling(TaskSwitch update) {
        scheduledTaskSwitch = update;
    }
}
