package dev.gxlg.librgetter.utils.types.messages.objects.configScreen;

import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.types.messages.Message;
import dev.gxlg.versiont.gen.net.minecraft.ChatFormatting;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.MutableComponent;

public abstract class PageContent extends Message {
    private final String modVersion;

    protected PageContent(String modVersion) {
        this.modVersion = modVersion;
    }

    protected MutableComponent title() {
        return Texts.literal("").append(Texts.literal("LibrGetter " + modVersion + "\n").withStyle(ChatFormatting.DARK_GREEN()));
    }
}
