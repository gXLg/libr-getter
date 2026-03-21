package dev.gxlg.librgetter.utils.types.messages.translatable.error;

import dev.gxlg.librgetter.utils.types.messages.translatable.TranslatableMessage;
import dev.gxlg.versiont.gen.net.minecraft.ChatFormatting;

public abstract class TranslatableErrorMessage extends TranslatableMessage {
    public TranslatableErrorMessage(String key, Object... arguments) {
        super(key, arguments);
    }

    @Override
    protected ChatFormatting getColor() {
        return ChatFormatting.RED();
    }
}
