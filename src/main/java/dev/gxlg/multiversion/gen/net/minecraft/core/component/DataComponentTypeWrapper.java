package dev.gxlg.multiversion.gen.net.minecraft.core.component;

import dev.gxlg.multiversion.R;

public class DataComponentTypeWrapper extends R.RWrapper<DataComponentTypeWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_9331/net.minecraft.core.component.DataComponentType");

    protected DataComponentTypeWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public static DataComponentTypeWrapper inst(Object instance) {
        return new DataComponentTypeWrapper(instance);
    }
}