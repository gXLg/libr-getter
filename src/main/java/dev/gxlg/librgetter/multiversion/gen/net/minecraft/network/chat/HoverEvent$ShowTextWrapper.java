package dev.gxlg.librgetter.multiversion.gen.net.minecraft.network.chat;

import dev.gxlg.librgetter.multiversion.R;

public class HoverEvent$ShowTextWrapper extends R.RWrapper<HoverEvent$ShowTextWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_2568$class_10613/net.minecraft.network.chat.HoverEvent$ShowText");

    public HoverEvent$ShowTextWrapper(dev.gxlg.librgetter.multiversion.gen.net.minecraft.network.chat.ComponentWrapper text) {
        this(clazz.constr(dev.gxlg.librgetter.multiversion.gen.net.minecraft.network.chat.ComponentWrapper.clazz).newInst(text.unwrap()).self());
    }

    protected HoverEvent$ShowTextWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public static HoverEvent$ShowTextWrapper inst(Object instance) {
        return new HoverEvent$ShowTextWrapper(instance);
    }
}
