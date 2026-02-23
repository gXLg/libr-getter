package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.chaining.villagers.Villagers;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.tasks.PickedAnotherProfessionException;
import dev.gxlg.librgetter.worker.scheduling.controllers.TaskSchedulerController;
import dev.gxlg.librgetter.worker.types.context.TaskContext;
import dev.gxlg.librgetter.worker.types.switcher.TaskSwitch;
import dev.gxlg.librgetter.worker.types.task.Task;

public class WaitVillagerAcceptProfessionTask extends Task {
    private int timeout = 0;

    @Override
    public void work(TaskContext taskContext, TaskSchedulerController controller) throws LibrGetterException {
        if (!Villagers.isVillagerUnemployed(taskContext.selectedVillager())) {
            if (!Villagers.isVillagerLibrarian(taskContext.selectedVillager())) {
                throw new PickedAnotherProfessionException();
            }
            controller.scheduleTaskSwitch(TaskSwitch.sameTick(RequestTradesTask::new));
            return;
        }
        if (LibrGetter.config.timeout != 0) {
            timeout++;
            // break and place the lectern again after a timeout
            if (timeout >= LibrGetter.config.timeout * 20) {
                controller.scheduleTaskSwitch(TaskSwitch.sameTick(SelectAxeTask::new));
            }
        }
    }
}
