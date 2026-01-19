package dev.gxlg.multiversion.gen.net.minecraft.world.item.component;

import dev.gxlg.multiversion.R;

public class CustomDataWrapper extends R.RWrapper<CustomDataWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_9279/net.minecraft.world.item.component.CustomData");

    protected CustomDataWrapper(Object instance) {
        super(instance);
        R.RInstance rInstance = clazz.inst(instance);
    }

    public dev.gxlg.multiversion.gen.net.minecraft.nbt.CompoundTagWrapper copyTag(){
        return dev.gxlg.multiversion.gen.net.minecraft.nbt.CompoundTagWrapper.inst(clazz.inst(this.instance).mthd("method_57461/copyTag").invk());
    }

    public static CustomDataWrapper inst(Object instance) {
        return new CustomDataWrapper(instance);
    }
}