package dev.gxlg.librgetter.utils.types.messages.translatable.success;

import dev.gxlg.librgetter.utils.types.messages.translatable.TranslatableMessage;
import dev.gxlg.versiont.gen.net.minecraft.ChatFormatting;

public class TranslatableSuccessMessage extends TranslatableMessage {
    public TranslatableSuccessMessage(String key, Object... arguments) {
        super(key, arguments);
    }

    @Override
    protected ChatFormatting getColor() {
        return ChatFormatting.GREEN();
    }
}
