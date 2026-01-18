package dev.gxlg.librgetter.utils.types.exceptions.commands;

import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.types.translatable_messages.error.CouldNotFindLecternMessage;

public class CouldNotFindLecternException extends LibrGetterException {
    public CouldNotFindLecternException() {
        super(new CouldNotFindLecternMessage());
    }
}
