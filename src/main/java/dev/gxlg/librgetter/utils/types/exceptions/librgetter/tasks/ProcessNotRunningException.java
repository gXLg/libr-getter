package dev.gxlg.librgetter.utils.types.exceptions.librgetter.tasks;

import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.translatable_messages.error.ProcessNotRunningMessage;

public class ProcessNotRunningException extends LibrGetterException {
    public ProcessNotRunningException() {
        super(new ProcessNotRunningMessage());
    }
}
