package dev.gxlg.librgetter.utils.types.exceptions.librgetter.commands;

import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.translatable_messages.error.VillagerNotLibrarianMessage;

public class VillagerNotLibrarianException extends LibrGetterException {
    public VillagerNotLibrarianException() {
        super(new VillagerNotLibrarianMessage());
    }
}
