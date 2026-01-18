package dev.gxlg.librgetter.utils.types.messages.partial;

import net.minecraft.ChatFormatting;

public class TranslatableConfigMenuName extends TranslatablePartialMessage {
    public TranslatableConfigMenuName() {
        super("librgetter.partial.menu");
    }

    @Override
    public ChatFormatting getColor() {
        return ChatFormatting.RESET;
    }
}
