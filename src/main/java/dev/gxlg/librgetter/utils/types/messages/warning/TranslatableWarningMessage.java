package dev.gxlg.librgetter.utils.types.messages.warning;

import dev.gxlg.librgetter.utils.types.messages.TranslatableMessage;
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
