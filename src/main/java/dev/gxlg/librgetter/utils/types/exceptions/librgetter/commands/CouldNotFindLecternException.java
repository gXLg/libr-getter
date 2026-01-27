package dev.gxlg.librgetter.utils.types.exceptions.librgetter.commands;

import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.messages.translatable.error.CouldNotFindLecternMessage;

public class CouldNotFindLecternException extends LibrGetterException {
    public CouldNotFindLecternException() {
        super(new CouldNotFindLecternMessage());
    }
}
