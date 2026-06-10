package dev.gxlg.librgetter.utils.exceptions.tasks;

import dev.gxlg.librgetter.utils.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.messages.translatable.error.NoLibrarianSetMessage;

public class NoLibrarianSetException extends LibrGetterException {
    public NoLibrarianSetException() {
        super(new NoLibrarianSetMessage());
    }
}
