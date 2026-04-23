package dev.gxlg.librgetter.utils.exceptions.tasks;

import dev.gxlg.librgetter.utils.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.messages.translatable.error.CanNotLockMessage;

public class CanNotLockException extends LibrGetterException {
    public CanNotLockException() {
        super(new CanNotLockMessage());
    }
}
