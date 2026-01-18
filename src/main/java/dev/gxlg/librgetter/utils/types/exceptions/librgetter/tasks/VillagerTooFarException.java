package dev.gxlg.librgetter.utils.types.exceptions.librgetter.tasks;

import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.translatable_messages.error.VillagerTooFarMessage;

public class VillagerTooFarException extends LibrGetterException {
    public VillagerTooFarException() {
        super(new VillagerTooFarMessage());
    }
}
