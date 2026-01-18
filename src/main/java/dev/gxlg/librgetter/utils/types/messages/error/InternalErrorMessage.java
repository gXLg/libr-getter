package dev.gxlg.librgetter.utils.types.messages.error;

public class InternalErrorMessage extends TranslatableErrorMessage {
    public InternalErrorMessage(String varName, String context) {
        super("librgetter.error.internal", varName, context);
    }

    public InternalErrorMessage(String varName) {
        super("librgetter.error.internal", varName, StackWalker.getInstance().getCallerClass().getName());
    }
}
