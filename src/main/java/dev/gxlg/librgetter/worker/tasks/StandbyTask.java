package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.worker.TaskManager;

public class StandbyTask extends TaskManager.Task {
    @Override
    public void work(TaskManager.TaskContext taskContext) {
    }

    @Override
    public boolean allowsBreaking() {
        return true;
    }

    @Override
    public boolean allowsPlacing() {
        return true;
    }
}
