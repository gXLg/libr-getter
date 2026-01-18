package dev.gxlg.librgetter.utils.types.translatable_messages.success;

import dev.gxlg.librgetter.utils.types.translatable_messages.TranslatableMessage;
import net.minecraft.ChatFormatting;

public class TranslatableSuccessMessage extends TranslatableMessage {
    public TranslatableSuccessMessage(String key, Object... arguments) {
        super(key, arguments);
    }

    @Override
    public ChatFormatting getColor() {
        return ChatFormatting.GREEN;
    }
}
