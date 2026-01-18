package dev.gxlg.librgetter.utils.types.exceptions.tasks;

import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.types.translatable_messages.error.VillagerTooFarMessage;

public class VillagerTooFarException extends LibrGetterException {
    public VillagerTooFarException() {
        super(new VillagerTooFarMessage());
    }
}
