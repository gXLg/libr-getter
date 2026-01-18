package dev.gxlg.librgetter.utils.types.exceptions.librgetter.tasks;

import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.translatable_messages.error.NoLibrarianSetMessage;

public class NoLibrarianSetException extends LibrGetterException {
    public NoLibrarianSetException() {
        super(new NoLibrarianSetMessage());
    }
}
