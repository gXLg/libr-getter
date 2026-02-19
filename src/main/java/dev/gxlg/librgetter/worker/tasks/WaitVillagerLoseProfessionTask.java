package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.chaining.villagers.Villagers;
import dev.gxlg.librgetter.utils.types.exceptions.signals.StopTaskSignal;
import dev.gxlg.librgetter.worker.TaskManager;

public class WaitVillagerLoseProfessionTask extends TaskManager.Task {
    @Override
    public void work(TaskManager.TaskContext taskContext) throws StopTaskSignal {
        if (!LibrGetter.config.waitLose || Villagers.isVillagerUnemployed(taskContext.selectedVillager())) {
            throw new StopTaskSignal(ctx -> TaskManager.TaskSwitch.sameTick(new SelectAndPlaceLecternTask(), ctx));
        }
    }
}
