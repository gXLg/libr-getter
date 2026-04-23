package dev.gxlg.librgetter.utils.exceptions.tasks;

import dev.gxlg.librgetter.utils.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.messages.translatable.error.NoLecternSetMessage;

public class NoLecternSetException extends LibrGetterException {
    public NoLecternSetException() {
        super(new NoLecternSetMessage());
    }
}
