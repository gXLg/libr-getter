package dev.gxlg.multiversion.gen.net.minecraft.core.component;

import dev.gxlg.multiversion.R;

public class DataComponentHolderWrapper extends R.RWrapper<DataComponentHolderWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_9322/net.minecraft.core.component.DataComponentHolder");

    protected DataComponentHolderWrapper(Object instance) {
        super(instance);
        R.RInstance rInstance = clazz.inst(instance);
    }

    public Object get(dev.gxlg.multiversion.gen.net.minecraft.core.component.DataComponentTypeWrapper type){
        return clazz.inst(this.instance).mthd("method_58694/get", dev.gxlg.multiversion.gen.net.minecraft.core.component.DataComponentTypeWrapper.clazz).invk(type.unwrap());
    }

    public static DataComponentHolderWrapper inst(Object instance) {
        return new DataComponentHolderWrapper(instance);
    }
}