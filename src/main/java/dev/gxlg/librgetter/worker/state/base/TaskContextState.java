package dev.gxlg.librgetter.worker.state.base;

import dev.gxlg.librgetter.worker.types.context.TaskContext;

public class TaskContextState {
    private TaskContext currentTaskContext = TaskContext.EMPTY;

    public TaskContext getTaskContext() {
        return currentTaskContext;
    }

    public void setTaskContext(TaskContext currentTaskContext) {
        this.currentTaskContext = currentTaskContext;
    }
}
