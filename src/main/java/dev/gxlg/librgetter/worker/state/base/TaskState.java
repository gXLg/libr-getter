package dev.gxlg.librgetter.worker.state.base;

import dev.gxlg.librgetter.worker.tasks.StandbyTask;
import dev.gxlg.librgetter.worker.types.task.Permissions;
import dev.gxlg.librgetter.worker.types.task.Task;

public class TaskState {
    private Task currentTask;

    public boolean isWorking() {
        return !(currentTask instanceof StandbyTask);
    }

    public Permissions getPermissions() {
        return currentTask.getPermissions();
    }

    public void setTask(Task currentTask) {
        this.currentTask = currentTask;
    }
}
