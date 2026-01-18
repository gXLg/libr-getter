package dev.gxlg.librgetter.utils.types.exceptions.common;

import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.types.translatable_messages.error.InternalErrorMessage;

public class InternalErrorException extends LibrGetterException {
    public InternalErrorException(String varName) {
        super(new InternalErrorMessage(varName, StackWalker.getInstance().getCallerClass().getName()));
    }
}
