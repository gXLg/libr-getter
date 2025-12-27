package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.reflection.Minecraft;
import dev.gxlg.librgetter.worker.Worker;

public class WaitVillagerLoseProfession extends Worker.Task {
    public WaitVillagerLoseProfession(Worker.TaskContext taskContext) {
        super(taskContext);
    }

    @Override
    public Worker.TaskSwitch work() {
        if (!LibrGetter.config.waitLose) return switchSameTick(new SelectAndPlaceLecternTask(taskContext));
        if (!Minecraft.isVillagerUnemployed(taskContext.selectedVillager())) return noSwitch();
        return switchSameTick(new SelectAndPlaceLecternTask(taskContext));
    }

    @Override
    public boolean allowsPlacing() {
        return false;
    }
}
