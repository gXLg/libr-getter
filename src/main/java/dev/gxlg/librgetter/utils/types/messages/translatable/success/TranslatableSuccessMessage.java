package dev.gxlg.librgetter.utils.types.messages.translatable.success;

import dev.gxlg.librgetter.utils.types.messages.translatable.TranslatableMessage;
import dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper;

public class TranslatableSuccessMessage extends TranslatableMessage {
    public TranslatableSuccessMessage(String key, Object... arguments) {
        super(key, arguments);
    }

    @Override
    protected ChatFormattingWrapper getColor() {
        return ChatFormattingWrapper.GREEN();
    }
}
