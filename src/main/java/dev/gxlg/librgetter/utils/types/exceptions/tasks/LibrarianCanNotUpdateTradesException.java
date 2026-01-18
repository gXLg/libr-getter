package dev.gxlg.librgetter.utils.types.exceptions.tasks;

import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.types.translatable_messages.error.LibrarianCanNotUpdateTradesMessage;

public class LibrarianCanNotUpdateTradesException extends LibrGetterException {
    public LibrarianCanNotUpdateTradesException() {
        super(new LibrarianCanNotUpdateTradesMessage());
    }
}
