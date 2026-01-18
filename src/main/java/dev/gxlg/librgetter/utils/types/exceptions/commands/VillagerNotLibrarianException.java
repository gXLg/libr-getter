package dev.gxlg.librgetter.utils.types.exceptions.commands;

import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.types.messages.error.VillagerNotLibrarianMessage;

public class VillagerNotLibrarianException extends LibrGetterException {
    public VillagerNotLibrarianException() {
        super(new VillagerNotLibrarianMessage());
    }
}
