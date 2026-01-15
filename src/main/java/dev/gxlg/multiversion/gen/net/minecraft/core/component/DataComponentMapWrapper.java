package dev.gxlg.multiversion.gen.net.minecraft.core.component;

import dev.gxlg.multiversion.R;

public class DataComponentMapWrapper extends R.RWrapper<DataComponentMapWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_9323/net.minecraft.core.component.DataComponentMap");

    protected DataComponentMapWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public static DataComponentMapWrapper inst(Object instance) {
        return new DataComponentMapWrapper(instance);
    }
}