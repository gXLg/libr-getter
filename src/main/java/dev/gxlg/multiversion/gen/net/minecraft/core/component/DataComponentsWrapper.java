package dev.gxlg.multiversion.gen.net.minecraft.core.component;

import dev.gxlg.multiversion.R;

public class DataComponentsWrapper extends R.RWrapper<DataComponentsWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_9334/net.minecraft.core.component.DataComponents");

    protected DataComponentsWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public static DataComponentsWrapper inst(Object instance) {
        return new DataComponentsWrapper(instance);
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.core.component.DataComponentTypeWrapper CUSTOM_DATA() {
        return dev.gxlg.multiversion.gen.net.minecraft.core.component.DataComponentTypeWrapper.inst(clazz.fld("field_49628/CUSTOM_DATA").get());
    }
}