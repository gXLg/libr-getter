package dev.gxlg.librgetter.utils.exceptions.tasks;

import dev.gxlg.librgetter.utils.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.messages.translatable.error.VillagerNotExistMessage;

public class VillagerNotExistException extends LibrGetterException {
    public VillagerNotExistException() {
        super(new VillagerNotExistMessage());
    }
}
