package dev.gxlg.librgetter.utils.exceptions.tasks;

import dev.gxlg.librgetter.utils.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.messages.translatable.error.ProcessNotRunningMessage;

public class ProcessNotRunningException extends LibrGetterException {
    public ProcessNotRunningException() {
        super(new ProcessNotRunningMessage());
    }
}
