package dev.gxlg.librgetter.utils.types.exceptions.librgetter.tasks;

import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.messages.translatable.error.ProcessNotRunningMessage;

public class ProcessNotRunningException extends LibrGetterException {
    public ProcessNotRunningException() {
        super(new ProcessNotRunningMessage());
    }
}
