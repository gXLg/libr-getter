package dev.gxlg.librgetter.utils.types.translatable_messages.feedback;

import dev.gxlg.librgetter.utils.types.translatable_messages.TranslatableMessage;
import net.minecraft.ChatFormatting;

public abstract class TranslatableFeedbackMessage extends TranslatableMessage {
    public TranslatableFeedbackMessage(String key, Object... arguments) {
        super(key, arguments);
    }

    @Override
    public ChatFormatting getColor() {
        return ChatFormatting.RESET;
    }
}
