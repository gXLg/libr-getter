package dev.gxlg.librgetter.utils.messages.translatable.warning;

import dev.gxlg.librgetter.utils.messages.translatable.TranslatableMessage;
import dev.gxlg.versiont.gen.net.minecraft.ChatFormatting;

public class TranslatableWarningMessage extends TranslatableMessage {
    public TranslatableWarningMessage(String key, Object... arguments) {
        super(key, arguments);
    }

    @Override
    protected ChatFormatting getColor() {
        return ChatFormatting.YELLOW();
    }
}
