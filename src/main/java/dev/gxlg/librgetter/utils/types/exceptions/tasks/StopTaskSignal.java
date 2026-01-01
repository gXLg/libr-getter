package dev.gxlg.librgetter.utils.types.exceptions.tasks;

import dev.gxlg.librgetter.worker.TaskManager;

import java.util.function.Function;

public class StopTaskSignal extends Exception {
    private final Function<TaskManager.TaskContext, TaskManager.TaskSwitch> taskSwitcher;

    public StopTaskSignal(Function<TaskManager.TaskContext, TaskManager.TaskSwitch> switcher) {
        taskSwitcher = switcher;
    }

    public void switchTask() {
        TaskManager.switchTask(taskSwitcher);
    }
}
