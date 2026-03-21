package dev.gxlg.librgetter.utils.types.exceptions.librgetter.commands;

import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.messages.translatable.error.NothingTargetedMessage;

public class NothingTargetedException extends LibrGetterException {
    public NothingTargetedException() {
        super(new NothingTargetedMessage());
    }
}
