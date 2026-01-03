package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.reflection.Minecraft;
import dev.gxlg.librgetter.utils.types.exceptions.tasks.StopTaskSignal;
import dev.gxlg.librgetter.worker.TaskManager;

public class WaitVillagerLoseProfessionTask extends TaskManager.Task {
    @Override
    public void work(TaskManager.TaskContext taskContext) throws StopTaskSignal {
        if (!LibrGetter.config.waitLose || Minecraft.isVillagerUnemployed(taskContext.selectedVillager())) {
            throw new StopTaskSignal(ctx -> TaskManager.TaskSwitch.sameTick(new SelectAndPlaceLecternTask(), ctx));
        }
    }
}
