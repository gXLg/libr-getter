package dev.gxlg.librgetter.utils.types.exceptions.parser;

import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.types.messages.translatable.error.CouldNotParseCustomMessage;

public class CouldNotParseCustomException extends LibrGetterException {
    public CouldNotParseCustomException() {
        super(new CouldNotParseCustomMessage());
    }
}
