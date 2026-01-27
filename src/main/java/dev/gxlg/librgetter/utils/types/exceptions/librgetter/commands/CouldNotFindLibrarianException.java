package dev.gxlg.librgetter.utils.types.exceptions.librgetter.commands;

import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.messages.translatable.error.CouldNotFindLibrarianMessage;

public class CouldNotFindLibrarianException extends LibrGetterException {
    public CouldNotFindLibrarianException() {
        super(new CouldNotFindLibrarianMessage());
    }
}
