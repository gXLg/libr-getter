package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.reflection.Minecraft;
import dev.gxlg.librgetter.worker.Worker;

public class WaitVillagerAcceptProfessionTask extends Worker.Task {
    private int timeout = 0;

    public WaitVillagerAcceptProfessionTask(Worker.TaskContext taskContext) {
        super(taskContext);
    }

    @Override
    public Worker.TaskSwitch work() {
        if (Minecraft.isVillagerEmployed(taskContext.selectedVillager())) {
            if (!Minecraft.isVillagerLibrarian(taskContext.selectedVillager())) {
                return error("librgetter.pick");
            }
            return switchSameTick(new RequestTradesTask(taskContext));
        }
        if (LibrGetter.config.timeout != 0) {
            timeout++;
            // break and place the lectern again after a timeout
            if (timeout >= LibrGetter.config.timeout * 20) return switchSameTick(new BreakLecternTask(taskContext));
        }
        return noSwitch();
    }
}
