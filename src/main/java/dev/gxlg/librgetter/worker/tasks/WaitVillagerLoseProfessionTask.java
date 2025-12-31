package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.reflection.Minecraft;
import dev.gxlg.librgetter.worker.Worker;

public class WaitVillagerLoseProfessionTask extends Worker.Task {
    public WaitVillagerLoseProfessionTask(Worker.TaskContext taskContext) {
        super(taskContext);
    }

    @Override
    public Worker.TaskSwitch work() {
        if (!LibrGetter.config.waitLose || Minecraft.isVillagerUnemployed(taskContext.selectedVillager()))
            return switchSameTick(new SelectAndPlaceLecternTask(taskContext));
        return noSwitch();
    }

    @Override
    public boolean allowsPlacing() {
        return false;
    }
}
