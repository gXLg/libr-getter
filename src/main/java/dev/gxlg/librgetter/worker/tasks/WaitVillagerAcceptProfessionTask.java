package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.chaining.villagers.Villagers;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.tasks.PickedAnotherProfessionException;
import dev.gxlg.librgetter.utils.types.exceptions.signals.StopTaskSignal;
import dev.gxlg.librgetter.worker.TaskManager;

public class WaitVillagerAcceptProfessionTask extends TaskManager.Task {
    private int timeout = 0;

    @Override
    public void work(TaskManager.TaskContext taskContext) throws StopTaskSignal, LibrGetterException {
        if (!Villagers.isVillagerUnemployed(taskContext.selectedVillager())) {
            if (!Villagers.isVillagerLibrarian(taskContext.selectedVillager())) {
                throw new PickedAnotherProfessionException();
            }
            throw new StopTaskSignal(ctx -> TaskManager.TaskSwitch.sameTick(new RequestTradesTask(), ctx));
        }
        if (LibrGetter.config.timeout != 0) {
            timeout++;
            // break and place the lectern again after a timeout
            if (timeout >= LibrGetter.config.timeout * 20) {
                throw new StopTaskSignal(ctx -> TaskManager.TaskSwitch.sameTick(new SelectAxeTask(), ctx));
            }
        }
    }
}
