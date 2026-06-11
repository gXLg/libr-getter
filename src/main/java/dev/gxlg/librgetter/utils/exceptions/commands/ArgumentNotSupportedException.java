package dev.gxlg.librgetter.utils.exceptions.commands;

import dev.gxlg.librgetter.utils.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.messages.translatable.error.ArgumentNotSupportedMessage;

public class ArgumentNotSupportedException extends LibrGetterException {
    public ArgumentNotSupportedException() {
        super(new ArgumentNotSupportedMessage());
    }
}
