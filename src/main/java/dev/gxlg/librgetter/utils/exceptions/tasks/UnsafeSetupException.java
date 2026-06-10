package dev.gxlg.librgetter.utils.exceptions.tasks;

import dev.gxlg.librgetter.utils.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.messages.translatable.error.UnsafeSetupMessage;

public class UnsafeSetupException extends LibrGetterException {
    public UnsafeSetupException() {
        super(new UnsafeSetupMessage());
    }
}
