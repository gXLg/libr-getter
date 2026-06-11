package dev.gxlg.librgetter.utils.exceptions.tasks;

import dev.gxlg.librgetter.utils.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.messages.translatable.error.LibrarianCanNotUpdateTradesMessage;

public class LibrarianCanNotUpdateTradesException extends LibrGetterException {
    public LibrarianCanNotUpdateTradesException() {
        super(new LibrarianCanNotUpdateTradesMessage());
    }
}
