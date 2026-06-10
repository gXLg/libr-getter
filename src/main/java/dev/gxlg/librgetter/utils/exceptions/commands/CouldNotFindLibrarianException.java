package dev.gxlg.librgetter.utils.exceptions.commands;

import dev.gxlg.librgetter.utils.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.messages.translatable.error.CouldNotFindLibrarianMessage;

public class CouldNotFindLibrarianException extends LibrGetterException {
    public CouldNotFindLibrarianException() {
        super(new CouldNotFindLibrarianMessage());
    }
}
