package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.worker.Worker;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.function.Function;

public class StandbyTask extends Worker.Task {
    private final Queue<Function<Worker.TaskContext, Worker.TaskContext>> contextUpdates = new ArrayDeque<>();
    private Function<Worker.TaskContext, Worker.Task> taskSwitcher = null;

    public StandbyTask(Worker.TaskContext taskContext) {
        super(taskContext);
    }

    @Override
    public Worker.TaskSwitch work() {
        if (contextUpdates.isEmpty() && taskSwitcher == null) return noSwitch();
        Worker.TaskContext newContext = taskContext;
        while (!contextUpdates.isEmpty()) {
            newContext = contextUpdates.remove().apply(newContext);
        }
        if (taskSwitcher == null) {
            return switchNextTick(new StandbyTask(newContext));
        } else {
            return switchNextTick(taskSwitcher.apply(newContext));
        }
    }

    public void updateContext(Function<Worker.TaskContext, Worker.TaskContext> updater) {
        contextUpdates.add(updater);
    }

    public void switchTask(Function<Worker.TaskContext, Worker.Task> switcher) {
        if (taskSwitcher != null) throw new IllegalStateException("Can't switch to a task more than once");
        taskSwitcher = switcher;
    }
}
