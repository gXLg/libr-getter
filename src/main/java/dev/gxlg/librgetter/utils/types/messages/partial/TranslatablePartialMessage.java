package dev.gxlg.librgetter.utils.types.messages.partial;

import dev.gxlg.librgetter.utils.types.messages.TranslatableMessage;

public abstract class TranslatablePartialMessage extends TranslatableMessage {
    public TranslatablePartialMessage(String key, Object... args) {
        super(key, args);
    }
}
