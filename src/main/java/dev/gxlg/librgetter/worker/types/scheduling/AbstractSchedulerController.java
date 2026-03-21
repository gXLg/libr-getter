package dev.gxlg.librgetter.worker.types.scheduling;

import dev.gxlg.librgetter.worker.scheduling.base.TaskContextUpdateScheduler;
import dev.gxlg.librgetter.worker.scheduling.base.TaskSwitchScheduler;
import dev.gxlg.librgetter.worker.types.context.TaskContextBuilder;
import dev.gxlg.librgetter.worker.types.switcher.TaskSwitch;

import java.util.function.Consumer;

public abstract class AbstractSchedulerController {
    private final TaskContextUpdateScheduler contextScheduler;

    private final TaskSwitchScheduler taskScheduler;

    protected AbstractSchedulerController(TaskContextUpdateScheduler contextScheduler, TaskSwitchScheduler taskScheduler) {
        this.contextScheduler = contextScheduler;
        this.taskScheduler = taskScheduler;
    }

    public void scheduleContextUpdate(Consumer<TaskContextBuilder> contextUpdater) {
        contextScheduler.scheduleUpdate(contextUpdater, getSchedulerContext());
    }

    public void scheduleTaskSwitch(TaskSwitch taskSwitch) {
        taskScheduler.scheduleUpdate(taskSwitch, getSchedulerContext());
    }

    protected abstract SchedulerContext getSchedulerContext();
}
