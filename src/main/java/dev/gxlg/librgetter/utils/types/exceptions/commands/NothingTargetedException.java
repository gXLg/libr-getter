package dev.gxlg.librgetter.utils.types.exceptions.commands;

import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.types.messages.error.NothingTargetedMessage;

public class NothingTargetedException extends LibrGetterException {
    public NothingTargetedException() {
        super(new NothingTargetedMessage());
    }
}
