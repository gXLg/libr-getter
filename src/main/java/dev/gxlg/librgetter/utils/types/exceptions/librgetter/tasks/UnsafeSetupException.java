package dev.gxlg.librgetter.utils.types.exceptions.librgetter.tasks;

import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.translatable_messages.error.UnsafeSetupMessage;

public class UnsafeSetupException extends LibrGetterException {
    public UnsafeSetupException() {
        super(new UnsafeSetupMessage());
    }
}
