package dev.gxlg.multiversion.gen.net.minecraft.core;

import dev.gxlg.multiversion.R;

public class RegistryWrapper extends R.RWrapper<RegistryWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_2378/net.minecraft.core.Registry");

    protected RegistryWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public dev.gxlg.multiversion.gen.net.minecraft.resources.ResourceKeyWrapper key(){
        return dev.gxlg.multiversion.gen.net.minecraft.resources.ResourceKeyWrapper.inst(this.instance.mthd("method_30517/key").invk());
    }

    public net.minecraft.resources.Identifier getKey(Object resource){
        return (net.minecraft.resources.Identifier) this.instance.mthd("method_10221/getKey", Object.class).invk(resource);
    }

    public static RegistryWrapper inst(Object instance) {
        return new RegistryWrapper(instance);
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.core.RegistryWrapper ENCHANTMENT() {
        return dev.gxlg.multiversion.gen.net.minecraft.core.RegistryWrapper.inst(clazz.fld("field_11160/ENCHANTMENT").get());
    }
}