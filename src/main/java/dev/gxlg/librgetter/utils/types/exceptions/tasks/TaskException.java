package dev.gxlg.librgetter.utils.types.exceptions.tasks;

import dev.gxlg.librgetter.utils.reflection.chaining.texts.Texts;

public class TaskException extends FinishSignal {
    public TaskException(String msg, String... args) {
        Texts.getImpl().sendTranslatableError(msg, (Object[]) args);
    }
}
