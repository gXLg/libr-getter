package dev.gxlg.librgetter.multiversion.gen.net.minecraft.core;

import dev.gxlg.librgetter.multiversion.R;

public class HolderWrapper extends R.RWrapper<HolderWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_6880/net.minecraft.core.Holder");

    protected HolderWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public static HolderWrapper inst(Object instance) {
        return new HolderWrapper(instance);
    }
}
