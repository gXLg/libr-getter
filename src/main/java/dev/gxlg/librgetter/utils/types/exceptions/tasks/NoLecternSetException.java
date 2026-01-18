package dev.gxlg.librgetter.utils.types.exceptions.tasks;

import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.types.messages.error.NoLecternSetMessage;

public class NoLecternSetException extends LibrGetterException {
    public NoLecternSetException() {
        super(new NoLecternSetMessage());
    }
}
