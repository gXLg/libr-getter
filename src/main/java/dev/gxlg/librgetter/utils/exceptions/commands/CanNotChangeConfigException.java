package dev.gxlg.librgetter.utils.exceptions.commands;

import dev.gxlg.librgetter.utils.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.messages.translatable.error.CanNotChangeConfigMessage;

public class CanNotChangeConfigException extends LibrGetterException {
    public CanNotChangeConfigException() {
        super(new CanNotChangeConfigMessage());
    }
}
