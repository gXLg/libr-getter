package dev.gxlg.librgetter.utils.types.translatable_messages.config;

import dev.gxlg.librgetter.utils.types.translatable_messages.TranslatableMessage;
import net.minecraft.ChatFormatting;

public class TranslatableCategory extends TranslatableMessage {
    public TranslatableCategory(String category) {
        super("librgetter.category." + category);
    }

    @Override
    public ChatFormatting getColor() {
        return ChatFormatting.RESET;
    }
}
