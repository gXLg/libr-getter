package dev.gxlg.librgetter.utils.types.exceptions.librgetter.commands;

import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.messages.translatable.error.ArgumentNotSupportedMessage;

public class ArgumentNotSupportedException extends LibrGetterException {
    public ArgumentNotSupportedException() {
        super(new ArgumentNotSupportedMessage());
    }
}
