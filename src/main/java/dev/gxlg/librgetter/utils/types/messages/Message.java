package dev.gxlg.librgetter.utils.types.messages;

import dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.network.chat.MutableComponentWrapper;

public abstract class Message {
    public final MutableComponentWrapper getComponent() {
        return buildComponent().withStyle(getColor());
    }

    protected abstract MutableComponentWrapper buildComponent();

    protected ChatFormattingWrapper getColor() {
        return ChatFormattingWrapper.RESET();
    }
}
