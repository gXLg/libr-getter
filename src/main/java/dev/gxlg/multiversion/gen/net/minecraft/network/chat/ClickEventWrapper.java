package dev.gxlg.multiversion.gen.net.minecraft.network.chat;

import dev.gxlg.multiversion.R;

public class ClickEventWrapper extends R.RWrapper<ClickEventWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_2558/net.minecraft.network.chat.ClickEvent");

    public ClickEventWrapper(dev.gxlg.multiversion.gen.net.minecraft.network.chat.ClickEvent$ActionWrapper action, String value){
        this(clazz.constr(dev.gxlg.multiversion.gen.net.minecraft.network.chat.ClickEvent$ActionWrapper.clazz, String.class).newInst(action.unwrap(), value).self());
    }

    protected ClickEventWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public static ClickEventWrapper inst(Object instance) {
        return new ClickEventWrapper(instance);
    }
}