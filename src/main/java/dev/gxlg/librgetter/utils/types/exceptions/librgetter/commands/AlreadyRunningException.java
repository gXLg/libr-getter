package dev.gxlg.librgetter.utils.types.exceptions.librgetter.commands;

import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.messages.translatable.error.AlreadyRunningMessage;

public class AlreadyRunningException extends LibrGetterException {
    public AlreadyRunningException() {
        super(new AlreadyRunningMessage());
    }
}
