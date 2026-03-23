package dev.gxlg.librgetter.utils.types.exceptions.commands;

import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.types.messages.translatable.error.ArgumentNotSupportedMessage;

public class ArgumentNotSupportedException extends LibrGetterException {
    public ArgumentNotSupportedException() {
        super(new ArgumentNotSupportedMessage());
    }
}
