package dev.gxlg.multiversion.gen.net.minecraft.core.component;

import dev.gxlg.multiversion.R;

public class DataComponentHolderWrapper extends R.RWrapper<DataComponentHolderWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_9322/net.minecraft.core.component.DataComponentHolder");

    protected DataComponentHolderWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public static DataComponentHolderWrapper inst(Object instance) {
        return new DataComponentHolderWrapper(instance);
    }
}