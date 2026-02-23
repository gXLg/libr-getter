package dev.gxlg.librgetter.worker.state;

import dev.gxlg.librgetter.worker.state.base.TaskContextState;
import dev.gxlg.librgetter.worker.state.base.TaskState;
import dev.gxlg.librgetter.worker.types.context.TaskContext;
import dev.gxlg.librgetter.worker.types.task.Permissions;

public class StateView {
    private final TaskState taskState;

    private final TaskContextState taskContextState;

    public StateView(TaskState taskState, TaskContextState taskContextState) {
        this.taskState = taskState;
        this.taskContextState = taskContextState;
    }

    public boolean isWorking() {
        return taskState.isWorking();
    }

    public Permissions getPermissions() {
        return taskState.getPermissions();
    }

    public TaskContext getTaskContext() {
        return taskContextState.getTaskContext();
    }
}
