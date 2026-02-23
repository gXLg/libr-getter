package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.chaining.villagers.Villagers;
import dev.gxlg.librgetter.worker.scheduling.controllers.TaskSchedulerController;
import dev.gxlg.librgetter.worker.types.context.TaskContext;
import dev.gxlg.librgetter.worker.types.switcher.TaskSwitch;
import dev.gxlg.librgetter.worker.types.task.Task;

public class WaitVillagerLoseProfessionTask extends Task {
    @Override
    public void work(TaskContext taskContext, TaskSchedulerController controller) {
        if (!LibrGetter.config.waitLose || Villagers.isVillagerUnemployed(taskContext.selectedVillager())) {
            controller.scheduleTaskSwitch(TaskSwitch.sameTick(SelectAndPlaceLecternTask::new));
        }
    }
}
