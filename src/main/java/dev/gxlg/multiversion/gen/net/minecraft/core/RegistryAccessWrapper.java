package dev.gxlg.multiversion.gen.net.minecraft.core;

import dev.gxlg.multiversion.R;

public class RegistryAccessWrapper extends R.RWrapper<RegistryAccessWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_5455/net.minecraft.core.RegistryAccess");

    protected RegistryAccessWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public dev.gxlg.multiversion.gen.net.minecraft.core.RegistryWrapper lookupOrThrow(dev.gxlg.multiversion.gen.net.minecraft.resources.ResourceKeyWrapper key){
        return dev.gxlg.multiversion.gen.net.minecraft.core.RegistryWrapper.inst(this.instance.mthd("method_30530/registryOrThrow/lookupOrThrow", dev.gxlg.multiversion.gen.net.minecraft.resources.ResourceKeyWrapper.clazz).invk(key.unwrap()));
    }

    public static RegistryAccessWrapper inst(Object instance) {
        return new RegistryAccessWrapper(instance);
    }
}