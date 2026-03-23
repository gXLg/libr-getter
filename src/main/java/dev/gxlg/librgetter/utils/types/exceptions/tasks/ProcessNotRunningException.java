package dev.gxlg.librgetter.utils.types.exceptions.tasks;

import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.types.messages.translatable.error.ProcessNotRunningMessage;

public class ProcessNotRunningException extends LibrGetterException {
    public ProcessNotRunningException() {
        super(new ProcessNotRunningMessage());
    }
}
