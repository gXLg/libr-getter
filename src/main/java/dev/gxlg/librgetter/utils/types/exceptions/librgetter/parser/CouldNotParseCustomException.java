package dev.gxlg.librgetter.utils.types.exceptions.librgetter.parser;

import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.messages.translatable.error.CouldNotParseCustomMessage;

public class CouldNotParseCustomException extends LibrGetterException {
    public CouldNotParseCustomException() {
        super(new CouldNotParseCustomMessage());
    }
}
