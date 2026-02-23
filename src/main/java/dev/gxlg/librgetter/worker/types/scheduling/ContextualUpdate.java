package dev.gxlg.librgetter.worker.types.scheduling;

import org.jetbrains.annotations.NotNull;

public record ContextualUpdate<T>(SchedulerContext context, T update) {
    public static <T> ContextualUpdate<T> prioritize(ContextualUpdate<T> oldUpdate, @NotNull ContextualUpdate<T> newUpdate) {
        if (oldUpdate == null) {
            return newUpdate;
        }
        if (oldUpdate.context().ordinal() < newUpdate.context().ordinal()) {
            return oldUpdate;
        } else {
            return newUpdate;
        }
    }
}
