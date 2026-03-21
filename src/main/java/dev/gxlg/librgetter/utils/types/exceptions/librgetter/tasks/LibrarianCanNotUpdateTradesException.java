package dev.gxlg.librgetter.utils.types.exceptions.librgetter.tasks;

import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.messages.translatable.error.LibrarianCanNotUpdateTradesMessage;

public class LibrarianCanNotUpdateTradesException extends LibrGetterException {
    public LibrarianCanNotUpdateTradesException() {
        super(new LibrarianCanNotUpdateTradesMessage());
    }
}
