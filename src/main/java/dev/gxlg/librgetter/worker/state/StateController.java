package dev.gxlg.librgetter.worker.state;

import dev.gxlg.librgetter.worker.state.base.TaskContextState;
import dev.gxlg.librgetter.worker.state.base.TaskState;
import dev.gxlg.librgetter.worker.types.context.TaskContext;
import dev.gxlg.librgetter.worker.types.task.Task;

public class StateController {
    private final TaskState taskState;

    private final TaskContextState taskContextState;

    public StateController(TaskState taskState, TaskContextState taskContextState) {
        this.taskState = taskState;
        this.taskContextState = taskContextState;
    }

    public void setTask(Task task) {
        taskState.setTask(task);
    }

    public void setTaskContext(TaskContext taskContext) {
        taskContextState.setTaskContext(taskContext);
    }
}
