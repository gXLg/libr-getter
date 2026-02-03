package dev.gxlg.librgetter.utils.types.messages.translatable;

import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.types.messages.Message;
import dev.gxlg.multiversion.gen.net.minecraft.network.chat.MutableComponentWrapper;

public abstract class TranslatableMessage extends Message {
    private final String translationKey;

    private final Object[] args;

    public TranslatableMessage(String key, Object... arguments) {
        translationKey = key;
        args = arguments;
    }

    @Override
    protected MutableComponentWrapper buildComponent() {
        return Texts.getImpl().translatable(translationKey, args);
    }
}
