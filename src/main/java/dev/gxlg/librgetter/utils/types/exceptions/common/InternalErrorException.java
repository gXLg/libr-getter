package dev.gxlg.librgetter.utils.types.exceptions.common;

import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.types.messages.translatable.error.InternalErrorMessage;

public class InternalErrorException extends LibrGetterException {
    public InternalErrorException(String varName) {
        super(new InternalErrorMessage(varName, StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass().getName()));
    }
}
