package dev.gxlg.librgetter.utils.types.exceptions.tasks;

import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.types.translatable_messages.error.EmptyGoalsListMessage;

public class EmptyGoalsListException extends LibrGetterException {
    public EmptyGoalsListException() {
        super(new EmptyGoalsListMessage());
    }
}
