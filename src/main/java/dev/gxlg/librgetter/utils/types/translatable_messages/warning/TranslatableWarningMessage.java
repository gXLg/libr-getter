package dev.gxlg.librgetter.utils.types.translatable_messages.warning;

import dev.gxlg.librgetter.utils.types.translatable_messages.TranslatableMessage;
import net.minecraft.ChatFormatting;

public class TranslatableWarningMessage extends TranslatableMessage {
    public TranslatableWarningMessage(String key, Object... arguments) {
        super(key, arguments);
    }

    @Override
    public ChatFormatting getColor() {
        return ChatFormatting.YELLOW;
    }
}
