package dev.gxlg.librgetter.utils.types.messages.partial;

import net.minecraft.ChatFormatting;

public class TranslatableRemoveButton extends TranslatablePartialMessage {
    public TranslatableRemoveButton() {
        super("librgetter.partial.remove");
    }

    @Override
    public ChatFormatting getColor() {
        return ChatFormatting.YELLOW;
    }
}
