package dev.gxlg.librgetter.utils.exceptions.commands;

import dev.gxlg.librgetter.utils.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.messages.translatable.error.AlreadyRunningMessage;

public class AlreadyRunningException extends LibrGetterException {
    public AlreadyRunningException() {
        super(new AlreadyRunningMessage());
    }
}
