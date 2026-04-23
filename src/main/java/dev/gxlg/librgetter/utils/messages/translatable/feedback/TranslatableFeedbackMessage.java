package dev.gxlg.librgetter.utils.messages.translatable.feedback;

import dev.gxlg.librgetter.utils.messages.translatable.TranslatableMessage;

public abstract class TranslatableFeedbackMessage extends TranslatableMessage {
    public TranslatableFeedbackMessage(String key, Object... arguments) {
        super(key, arguments);
    }
}
