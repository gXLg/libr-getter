package dev.gxlg.librgetter.utils.types.translatable_messages.config;

import dev.gxlg.librgetter.utils.types.translatable_messages.TranslatableMessage;
import net.minecraft.ChatFormatting;

public class TranslatableConfigDescription extends TranslatableMessage {
    public TranslatableConfigDescription(String configName) {
        super("librgetter.config." + configName);
    }

    @Override
    public ChatFormatting getColor() {
        return ChatFormatting.RESET;
    }
}
