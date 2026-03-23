package dev.gxlg.librgetter.utils.types.exceptions.tasks;

import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.types.messages.translatable.error.UnsafeSetupMessage;

public class UnsafeSetupException extends LibrGetterException {
    public UnsafeSetupException() {
        super(new UnsafeSetupMessage());
    }
}
