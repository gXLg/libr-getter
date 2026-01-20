package dev.gxlg.librgetter.utils.types.translatable_messages.config;

import dev.gxlg.librgetter.utils.types.config.helpers.Configurable;
import dev.gxlg.librgetter.utils.types.translatable_messages.TranslatableMessage;
import net.minecraft.ChatFormatting;

public class TranslatableConfigDescription extends TranslatableMessage {
    public TranslatableConfigDescription(Configurable<?> configurable) {
        super("librgetter.config." + configurable.name());
    }

    @Override
    public ChatFormatting getColor() {
        return ChatFormatting.RESET;
    }
}
