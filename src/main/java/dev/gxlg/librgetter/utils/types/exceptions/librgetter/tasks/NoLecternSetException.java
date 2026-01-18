package dev.gxlg.librgetter.utils.types.exceptions.librgetter.tasks;

import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.translatable_messages.error.NoLecternSetMessage;

public class NoLecternSetException extends LibrGetterException {
    public NoLecternSetException() {
        super(new NoLecternSetMessage());
    }
}
