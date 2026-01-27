package dev.gxlg.librgetter.utils.types.messages.translatable.feedback;

import dev.gxlg.librgetter.utils.types.messages.translatable.TranslatableMessage;

public abstract class TranslatableFeedbackMessage extends TranslatableMessage {
    public TranslatableFeedbackMessage(String key, Object... arguments) {
        super(key, arguments);
    }
}
