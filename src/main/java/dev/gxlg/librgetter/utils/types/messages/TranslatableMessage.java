package dev.gxlg.librgetter.utils.types.messages;

import net.minecraft.ChatFormatting;

public abstract class TranslatableMessage {
    private final String translationKey;

    private final Object[] args;

    public TranslatableMessage(String key, Object... arguments) {
        translationKey = key;
        args = arguments;
    }

    public abstract ChatFormatting getColor();

    public String getTranslationKey() {
        return translationKey;
    }

    public Object[] getArgs() {
        return args;
    }
}
