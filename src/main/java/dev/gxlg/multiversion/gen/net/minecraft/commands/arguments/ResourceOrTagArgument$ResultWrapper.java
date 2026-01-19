package dev.gxlg.multiversion.gen.net.minecraft.commands.arguments;

import dev.gxlg.multiversion.R;

public class ResourceOrTagArgument$ResultWrapper extends R.RWrapper<ResourceOrTagArgument$ResultWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_7737$class_7741/net.minecraft.commands.arguments.ResourceOrTagArgument$Result");

    protected ResourceOrTagArgument$ResultWrapper(Object instance) {
        super(instance);
        R.RInstance rInstance = clazz.inst(instance);
    }

    public com.mojang.datafixers.util.Either unwrap2(){
        return (com.mojang.datafixers.util.Either) clazz.inst(this.instance).mthd("method_45647/unwrap").invk();
    }

    public java.util.Optional cast(dev.gxlg.multiversion.gen.net.minecraft.resources.ResourceKeyWrapper registryKey){
        return (java.util.Optional) clazz.inst(this.instance).mthd("method_45648/cast", dev.gxlg.multiversion.gen.net.minecraft.resources.ResourceKeyWrapper.clazz).invk(registryKey.unwrap());
    }

    public static ResourceOrTagArgument$ResultWrapper inst(Object instance) {
        return new ResourceOrTagArgument$ResultWrapper(instance);
    }
}