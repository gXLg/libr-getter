package dev.gxlg.librgetter.utils.exceptions.tasks;

import dev.gxlg.librgetter.utils.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.messages.translatable.error.EmptyGoalsListMessage;

public class EmptyGoalsListException extends LibrGetterException {
    public EmptyGoalsListException() {
        super(new EmptyGoalsListMessage());
    }
}
