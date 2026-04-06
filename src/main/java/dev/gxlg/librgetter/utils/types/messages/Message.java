package dev.gxlg.librgetter.utils.types.messages;

import dev.gxlg.versiont.gen.net.minecraft.ChatFormatting;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.MutableComponent;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Style;

public abstract class Message {
    public final MutableComponent getComponent() {
        return buildComponent().withStyle(Style.EMPTY().withColor(getColor()));
    }

    protected abstract MutableComponent buildComponent();

    protected ChatFormatting getColor() {
        return ChatFormatting.RESET();
    }
}
