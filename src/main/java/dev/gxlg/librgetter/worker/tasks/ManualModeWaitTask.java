package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.worker.Worker;

public class ManualModeWaitTask extends Worker.Task {
    public ManualModeWaitTask(Worker.TaskContext taskContext) {
        super(taskContext);
    }

    @Override
    public boolean allowsBreaking() {
        return false;
    }
}
