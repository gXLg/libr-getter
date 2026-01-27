package dev.gxlg.librgetter.utils.types.exceptions.librgetter.commands;

import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.messages.translatable.error.VillagerNotLibrarianMessage;

public class VillagerNotLibrarianException extends LibrGetterException {
    public VillagerNotLibrarianException() {
        super(new VillagerNotLibrarianMessage());
    }
}
