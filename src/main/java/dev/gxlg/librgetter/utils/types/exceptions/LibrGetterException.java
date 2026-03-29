package dev.gxlg.librgetter.utils.types.exceptions;

import dev.gxlg.librgetter.utils.types.messages.translatable.error.TranslatableErrorMessage;

public abstract class LibrGetterException extends Exception {
    private final TranslatableErrorMessage message;

    public LibrGetterException(TranslatableErrorMessage message) {
        this.message = message;
    }

    public TranslatableErrorMessage getTranslatableErrorMessage() {
        return message;
    }
}
