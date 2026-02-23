package dev.gxlg.librgetter.worker.scheduling.controllers;

import dev.gxlg.librgetter.worker.scheduling.base.TaskContextUpdateScheduler;
import dev.gxlg.librgetter.worker.scheduling.base.TaskSwitchScheduler;
import dev.gxlg.librgetter.worker.types.scheduling.AbstractSchedulerController;
import dev.gxlg.librgetter.worker.types.scheduling.SchedulerContext;

public class SystemSchedulerController extends AbstractSchedulerController {
    public SystemSchedulerController(TaskContextUpdateScheduler contextScheduler, TaskSwitchScheduler taskScheduler) {
        super(contextScheduler, taskScheduler);
    }

    @Override
    protected SchedulerContext getSchedulerContext() {
        return SchedulerContext.SYSTEM;
    }
}
