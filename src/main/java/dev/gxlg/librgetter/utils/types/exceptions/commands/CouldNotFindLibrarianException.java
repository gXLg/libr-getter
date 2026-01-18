package dev.gxlg.librgetter.utils.types.exceptions.commands;

import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.types.translatable_messages.error.CouldNotFindLibrarianMessage;

public class CouldNotFindLibrarianException extends LibrGetterException {
    public CouldNotFindLibrarianException() {
        super(new CouldNotFindLibrarianMessage());
    }
}
