package dev.gxlg.librgetter.utils.types.translatable_messages.partial;

import net.minecraft.ChatFormatting;

public class TranslatableGoalsListName extends TranslatablePartialMessage {
    public TranslatableGoalsListName() {
        super("librgetter.partial.list");
    }

    @Override
    public ChatFormatting getColor() {
        return ChatFormatting.RESET;
    }
}
