package dev.gxlg.librgetter.utils.types.messages.objects.configScreen;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.types.messages.Message;
import dev.gxlg.versiont.gen.net.minecraft.ChatFormatting;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.MutableComponent;

public abstract class PageContent extends Message {
    protected static MutableComponent title() {
        return Texts.literal("").append(Texts.literal("LibrGetter " + LibrGetter.getVersion() + "\n").withStyle(ChatFormatting.DARK_GREEN()));
    }
}
