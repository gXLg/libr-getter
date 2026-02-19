package dev.gxlg.librgetter.utils.chaining.texts;

import dev.gxlg.versiont.gen.net.minecraft.network.chat.Component;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.MutableComponent;

public class Texts_1_19_0 extends Texts_1_17_0 {
    @Override
    protected MutableComponent createTranslatable(String message, Object... args) {
        return Component.translatable(message, args);
    }
}
