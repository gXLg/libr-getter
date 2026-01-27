package dev.gxlg.librgetter.utils.types.messages.translatable.error;

import dev.gxlg.librgetter.utils.types.messages.translatable.TranslatableMessage;
import dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper;

public abstract class TranslatableErrorMessage extends TranslatableMessage {
    public TranslatableErrorMessage(String key, Object... arguments) {
        super(key, arguments);
    }

    @Override
    protected ChatFormattingWrapper getColor() {
        return ChatFormattingWrapper.RED();
    }
}
