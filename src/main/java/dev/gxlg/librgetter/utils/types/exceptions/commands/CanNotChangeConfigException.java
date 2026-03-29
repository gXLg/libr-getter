package dev.gxlg.librgetter.utils.types.exceptions.commands;

import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.types.messages.translatable.error.CanNotChangeConfigMessage;

public class CanNotChangeConfigException extends LibrGetterException {
    public CanNotChangeConfigException() {
        super(new CanNotChangeConfigMessage());
    }
}
