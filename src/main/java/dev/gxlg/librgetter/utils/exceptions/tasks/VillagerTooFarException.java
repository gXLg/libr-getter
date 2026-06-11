package dev.gxlg.librgetter.utils.exceptions.tasks;

import dev.gxlg.librgetter.utils.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.messages.translatable.error.VillagerTooFarMessage;

public class VillagerTooFarException extends LibrGetterException {
    public VillagerTooFarException() {
        super(new VillagerTooFarMessage());
    }
}
