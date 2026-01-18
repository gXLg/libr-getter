package dev.gxlg.librgetter.utils.types.translatable_messages.partial;

import dev.gxlg.librgetter.utils.types.translatable_messages.TranslatableMessage;

public abstract class TranslatablePartialMessage extends TranslatableMessage {
    public TranslatablePartialMessage(String key, Object... args) {
        super(key, args);
    }
}
