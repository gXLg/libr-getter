package dev.gxlg.librgetter.utils.types.exceptions.librgetter.tasks;

import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.messages.translatable.error.NoLecternSetMessage;

public class NoLecternSetException extends LibrGetterException {
    public NoLecternSetException() {
        super(new NoLecternSetMessage());
    }
}
