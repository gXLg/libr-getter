package dev.gxlg.librgetter.utils.reflection.chaining.texts;

import dev.gxlg.multiversion.gen.net.minecraft.network.chat.ComponentWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.network.chat.MutableComponentWrapper;

public class Texts_1_19_0 extends Texts_1_17_0 {
    @Override
    protected MutableComponentWrapper translatable(String message, Object... args) {
        return ComponentWrapper.translatable(message, args);
    }
}
