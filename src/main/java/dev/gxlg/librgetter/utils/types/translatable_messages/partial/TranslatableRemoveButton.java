package dev.gxlg.librgetter.utils.types.translatable_messages.partial;

import net.minecraft.ChatFormatting;

public class TranslatableRemoveButton extends TranslatablePartialMessage {
    public TranslatableRemoveButton() {
        super("librgetter.partial.remove");
    }

    @Override
    public ChatFormatting getColor() {
        return ChatFormatting.DARK_AQUA;
    }
}
