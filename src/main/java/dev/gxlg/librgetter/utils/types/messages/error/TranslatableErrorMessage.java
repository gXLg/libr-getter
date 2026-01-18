package dev.gxlg.librgetter.utils.types.messages.error;

import dev.gxlg.librgetter.utils.types.messages.TranslatableMessage;
import net.minecraft.ChatFormatting;

public abstract class TranslatableErrorMessage extends TranslatableMessage {
    public TranslatableErrorMessage(String key, Object... arguments) {
        super(key, arguments);
    }

    @Override
    public ChatFormatting getColor() {
        return ChatFormatting.RED;
    }
}
