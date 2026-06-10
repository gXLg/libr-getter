package dev.gxlg.librgetter.utils.exceptions.commands;

import dev.gxlg.librgetter.utils.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.messages.translatable.error.VillagerNotLibrarianMessage;

public class VillagerNotLibrarianException extends LibrGetterException {
    public VillagerNotLibrarianException() {
        super(new VillagerNotLibrarianMessage());
    }
}
