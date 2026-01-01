package dev.gxlg.librgetter.utils.types.exceptions.tasks;

import dev.gxlg.librgetter.worker.TaskManager;
import dev.gxlg.librgetter.worker.tasks.StandbyTask;

public class FinishSignal extends StopTaskSignal {
    public FinishSignal() {
        super(ctx -> TaskManager.TaskSwitch.nextTick(new StandbyTask(), ctx));
    }
}
