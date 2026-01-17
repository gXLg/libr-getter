package dev.gxlg.multiversion.gen.net.minecraft.network.chat;

import dev.gxlg.multiversion.R;

public class HoverEventWrapper extends R.RWrapper<HoverEventWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_2568/net.minecraft.network.chat.HoverEvent");

    public HoverEventWrapper(dev.gxlg.multiversion.gen.net.minecraft.network.chat.HoverEvent$ActionWrapper action, Object contents){
        this(clazz.constr(dev.gxlg.multiversion.gen.net.minecraft.network.chat.HoverEvent$ActionWrapper.clazz, Object.class).newInst(action.unwrap(), contents).self());
    }

    protected HoverEventWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public static HoverEventWrapper inst(Object instance) {
        return new HoverEventWrapper(instance);
    }
}