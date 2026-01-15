package dev.gxlg.multiversion.gen.net.minecraft.network.chat;

import dev.gxlg.multiversion.R;

public class ClickEvent$ActionWrapper extends R.RWrapper<ClickEvent$ActionWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_2558$class_2559/net.minecraft.network.chat.ClickEvent$Action");

    protected ClickEvent$ActionWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public static ClickEvent$ActionWrapper inst(Object instance) {
        return new ClickEvent$ActionWrapper(instance);
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.network.chat.ClickEvent$ActionWrapper RUN_COMMAND() {
        return dev.gxlg.multiversion.gen.net.minecraft.network.chat.ClickEvent$ActionWrapper.inst(clazz.fld("field_11750/RUN_COMMAND").get());
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.network.chat.ClickEvent$ActionWrapper CHANGE_PAGE() {
        return dev.gxlg.multiversion.gen.net.minecraft.network.chat.ClickEvent$ActionWrapper.inst(clazz.fld("field_11748/CHANGE_PAGE").get());
    }
}