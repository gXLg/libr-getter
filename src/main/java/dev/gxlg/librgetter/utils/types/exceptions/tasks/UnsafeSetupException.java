package dev.gxlg.librgetter.utils.types.exceptions.tasks;

import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.types.translatable_messages.error.UnsafeSetupMessage;

public class UnsafeSetupException extends LibrGetterException {
    public UnsafeSetupException() {
        super(new UnsafeSetupMessage());
    }
}
