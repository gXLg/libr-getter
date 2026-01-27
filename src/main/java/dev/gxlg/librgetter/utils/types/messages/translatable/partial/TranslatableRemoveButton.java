package dev.gxlg.librgetter.utils.types.messages.translatable.partial;

import dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper;

public class TranslatableRemoveButton extends TranslatablePartialMessage {
    public TranslatableRemoveButton() {
        super("librgetter.partial.remove");
    }

    @Override
    public ChatFormattingWrapper getColor() {
        return ChatFormattingWrapper.GOLD();
    }
}
