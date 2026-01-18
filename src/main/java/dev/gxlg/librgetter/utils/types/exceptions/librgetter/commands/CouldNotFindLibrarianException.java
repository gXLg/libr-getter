package dev.gxlg.librgetter.utils.types.exceptions.librgetter.commands;

import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.translatable_messages.error.CouldNotFindLibrarianMessage;

public class CouldNotFindLibrarianException extends LibrGetterException {
    public CouldNotFindLibrarianException() {
        super(new CouldNotFindLibrarianMessage());
    }
}
