package dev.gxlg.librgetter.worker.state.base;

import dev.gxlg.librgetter.worker.tasks.StandbyTask;
import dev.gxlg.librgetter.worker.types.task.PermissionView;
import dev.gxlg.librgetter.worker.types.task.Task;

public class TaskState {
    private Task currentTask = new StandbyTask();

    public boolean isWorking() {
        return !(currentTask instanceof StandbyTask);
    }

    public PermissionView createPermissionView() {
        return currentTask.createPermissionView();
    }

    public void setTask(Task currentTask) {
        this.currentTask = currentTask;
    }
}
