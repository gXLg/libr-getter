package dev.gxlg.librgetter.utils.types.exceptions.librgetter.tasks;

import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.messages.translatable.error.EmptyGoalsListMessage;

public class EmptyGoalsListException extends LibrGetterException {
    public EmptyGoalsListException() {
        super(new EmptyGoalsListMessage());
    }
}
