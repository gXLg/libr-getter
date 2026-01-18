package dev.gxlg.librgetter.utils.types.exceptions.tasks;

import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.types.translatable_messages.error.NoLibrarianSetMessage;

public class NoLibrarianSetException extends LibrGetterException {
    public NoLibrarianSetException() {
        super(new NoLibrarianSetMessage());
    }
}
