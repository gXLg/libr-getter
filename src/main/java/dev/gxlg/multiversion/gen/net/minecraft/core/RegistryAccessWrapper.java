package dev.gxlg.multiversion.gen.net.minecraft.core;

import dev.gxlg.multiversion.R;

public class RegistryAccessWrapper extends R.RWrapper<RegistryAccessWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_5455/net.minecraft.core.RegistryAccess");

    protected RegistryAccessWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public static RegistryAccessWrapper inst(Object instance) {
        return new RegistryAccessWrapper(instance);
    }
}