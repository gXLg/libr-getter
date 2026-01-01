package dev.gxlg.librgetter.utils.types.exceptions.tasks;

import dev.gxlg.librgetter.worker.TaskManager;

public class InternalTaskException extends TaskException {
    public InternalTaskException(String varName, TaskManager.Task task) {
        super("librgetter.internal", varName, task.getClass().getSimpleName());
    }
}
