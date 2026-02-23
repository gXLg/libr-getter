package dev.gxlg.librgetter.worker.types.scheduling;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicReference;

public abstract class AbstractScheduler<T> {
    private final AtomicReference<ContextualUpdate<T>> updater = new AtomicReference<>(null);

    public void scheduleUpdate(@NotNull T update, SchedulerContext context) {
        ContextualUpdate<T> newUpdate = new ContextualUpdate<>(context, update);
        updater.getAndUpdate(oldUpdate -> ContextualUpdate.prioritize(oldUpdate, newUpdate));
    }

    public void handleScheduling() {
        ContextualUpdate<T> update = updater.getAndSet(null);
        if (update == null) {
            handleEmptyScheduling();
        } else {
            handleScheduling(update.update());
        }
    }

    protected void handleEmptyScheduling() {
    }

    protected abstract void handleScheduling(T update);
}
