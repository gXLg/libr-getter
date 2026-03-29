package dev.gxlg.librgetter.utils.types.messages.translatable.error;

public class InternalErrorMessage extends TranslatableErrorMessage {
    public InternalErrorMessage(String varName, String context) {
        super("librgetter.error.internal", varName, context);
    }
}
