package dev.gxlg.librgetter.worker.types.switcher;

import dev.gxlg.librgetter.worker.types.task.Task;

import java.util.function.Supplier;

public final class TaskSwitch {
    private final Supplier<Task> taskSupplier;

    private final boolean isSameTick;

    private TaskSwitch(Supplier<Task> taskSupplier, boolean isSameTick) {
        this.taskSupplier = taskSupplier;
        this.isSameTick = isSameTick;
    }

    public Supplier<Task> taskSupplier() {
        return taskSupplier;
    }

    public boolean isSameTick() {
        return isSameTick;
    }

    public static TaskSwitch sameTick(Supplier<Task> taskSupplier) {
        return new TaskSwitch(taskSupplier, true);
    }

    public static TaskSwitch nextTick(Supplier<Task> taskSupplier) {
        return new TaskSwitch(taskSupplier, false);
    }
}
