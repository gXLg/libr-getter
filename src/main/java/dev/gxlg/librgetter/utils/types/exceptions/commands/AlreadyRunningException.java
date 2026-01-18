package dev.gxlg.librgetter.utils.types.exceptions.commands;

import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.types.translatable_messages.error.AlreadyRunningMessage;

public class AlreadyRunningException extends LibrGetterException {
    public AlreadyRunningException() {
        super(new AlreadyRunningMessage());
    }
}
