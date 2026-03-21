package dev.gxlg.librgetter.utils.types.exceptions.librgetter.tasks;

import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.messages.translatable.error.CanNotLockMessage;

public class CanNotLockException extends LibrGetterException {
    public CanNotLockException() {
        super(new CanNotLockMessage());
    }
}
