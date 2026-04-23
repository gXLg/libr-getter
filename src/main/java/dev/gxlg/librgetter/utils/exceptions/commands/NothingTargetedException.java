package dev.gxlg.librgetter.utils.exceptions.commands;

import dev.gxlg.librgetter.utils.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.messages.translatable.error.NothingTargetedMessage;

public class NothingTargetedException extends LibrGetterException {
    public NothingTargetedException() {
        super(new NothingTargetedMessage());
    }
}
