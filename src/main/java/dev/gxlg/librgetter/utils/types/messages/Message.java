package dev.gxlg.librgetter.utils.types.messages;

import dev.gxlg.versiont.gen.net.minecraft.ChatFormatting;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.MutableComponent;

public abstract class Message {
    public final MutableComponent getComponent() {
        return buildComponent().withStyle(getColor());
    }

    protected abstract MutableComponent buildComponent();

    protected ChatFormatting getColor() {
        return ChatFormatting.RESET();
    }
}
