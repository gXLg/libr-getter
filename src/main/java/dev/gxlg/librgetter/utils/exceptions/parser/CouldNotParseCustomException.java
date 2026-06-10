package dev.gxlg.librgetter.utils.exceptions.parser;

import dev.gxlg.librgetter.utils.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.messages.translatable.error.CouldNotParseCustomMessage;

public class CouldNotParseCustomException extends LibrGetterException {
    public CouldNotParseCustomException() {
        super(new CouldNotParseCustomMessage());
    }
}
