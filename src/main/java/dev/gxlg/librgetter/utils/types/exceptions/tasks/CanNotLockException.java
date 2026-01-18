package dev.gxlg.librgetter.utils.types.exceptions.tasks;

import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.types.messages.error.CanNotLockMessage;

public class CanNotLockException extends LibrGetterException {
    public CanNotLockException() {
        super(new CanNotLockMessage());
    }
}
