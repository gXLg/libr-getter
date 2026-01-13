package dev.gxlg.librgetter.multiversion.gen.net.minecraft.core.component;

import dev.gxlg.librgetter.multiversion.R;

public class DataComponentsWrapper extends R.RWrapper<DataComponentsWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_9334/net.minecraft.core.component.DataComponents");

    protected DataComponentsWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public static DataComponentsWrapper inst(Object instance) {
        return new DataComponentsWrapper(instance);
    }
}
