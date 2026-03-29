package dev.gxlg.librgetter.worker.scheduling.base;

import dev.gxlg.librgetter.worker.types.context.TaskContext;
import dev.gxlg.librgetter.worker.types.context.TaskContextBuilder;
import dev.gxlg.librgetter.worker.types.scheduling.AbstractScheduler;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class TaskContextUpdateScheduler extends AbstractScheduler<Consumer<TaskContextBuilder>> {
    @NotNull
    private final TaskContextBuilder taskContextBuilder = new TaskContextBuilder();

    public TaskContext buildTaskContext() {
        return taskContextBuilder.build();
    }

    @Override
    protected void handleScheduling(Consumer<TaskContextBuilder> update) {
        update.accept(taskContextBuilder);
    }
}
