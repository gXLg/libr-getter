package dev.gxlg.librgetter.utils.types.messages.translatable.warning;

import dev.gxlg.librgetter.utils.types.messages.translatable.TranslatableMessage;
import dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper;

public class TranslatableWarningMessage extends TranslatableMessage {
    public TranslatableWarningMessage(String key, Object... arguments) {
        super(key, arguments);
    }

    @Override
    protected ChatFormattingWrapper getColor() {
        return ChatFormattingWrapper.YELLOW();
    }
}
