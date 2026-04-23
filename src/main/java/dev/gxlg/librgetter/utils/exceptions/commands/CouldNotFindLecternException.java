package dev.gxlg.librgetter.utils.exceptions.commands;

import dev.gxlg.librgetter.utils.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.messages.translatable.error.CouldNotFindLecternMessage;

public class CouldNotFindLecternException extends LibrGetterException {
    public CouldNotFindLecternException() {
        super(new CouldNotFindLecternMessage());
    }
}
