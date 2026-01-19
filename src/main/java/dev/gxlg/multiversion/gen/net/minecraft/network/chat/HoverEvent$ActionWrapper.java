package dev.gxlg.multiversion.gen.net.minecraft.network.chat;

import dev.gxlg.multiversion.R;

public class HoverEvent$ActionWrapper extends R.RWrapper<HoverEvent$ActionWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_2568$class_5247/net.minecraft.network.chat.HoverEvent$Action");

    protected HoverEvent$ActionWrapper(Object instance) {
        super(instance);
        R.RInstance rInstance = clazz.inst(instance);
    }

    public static HoverEvent$ActionWrapper inst(Object instance) {
        return new HoverEvent$ActionWrapper(instance);
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.network.chat.HoverEvent$ActionWrapper SHOW_TEXT() {
        return dev.gxlg.multiversion.gen.net.minecraft.network.chat.HoverEvent$ActionWrapper.inst(clazz.fld("field_24342/SHOW_TEXT").get());
    }
}