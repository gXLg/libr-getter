package dev.gxlg.librgetter.utils.types.messages.translatable.partial;

import dev.gxlg.librgetter.utils.types.messages.translatable.TranslatableMessage;

public abstract class TranslatablePartialMessage extends TranslatableMessage {
    public TranslatablePartialMessage(String key, Object... args) {
        super(key, args);
    }
}
