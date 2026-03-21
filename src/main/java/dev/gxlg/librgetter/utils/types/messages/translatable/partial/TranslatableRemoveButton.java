package dev.gxlg.librgetter.utils.types.messages.translatable.partial;

import dev.gxlg.versiont.gen.net.minecraft.ChatFormatting;

public class TranslatableRemoveButton extends TranslatablePartialMessage {
    public TranslatableRemoveButton() {
        super("librgetter.partial.remove");
    }

    @Override
    public ChatFormatting getColor() {
        return ChatFormatting.GOLD();
    }
}
