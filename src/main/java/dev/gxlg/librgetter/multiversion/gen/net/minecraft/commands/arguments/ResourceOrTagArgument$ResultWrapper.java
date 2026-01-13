package dev.gxlg.librgetter.multiversion.gen.net.minecraft.commands.arguments;

import dev.gxlg.librgetter.multiversion.R;

public class ResourceOrTagArgument$ResultWrapper extends R.RWrapper<ResourceOrTagArgument$ResultWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_7737$class_7741/net.minecraft.commands.arguments.ResourceOrTagArgument$Result");

    protected ResourceOrTagArgument$ResultWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public com.mojang.datafixers.util.Either unwrap() {
         return (com.mojang.datafixers.util.Either) instance.mthd("method_45647/unwrap").invk();
    }

    public java.util.Optional cast(dev.gxlg.librgetter.multiversion.gen.net.minecraft.resources.ResourceKeyWrapper registryKey) {
         return (java.util.Optional) instance.mthd("method_45648/cast", dev.gxlg.librgetter.multiversion.gen.net.minecraft.resources.ResourceKeyWrapper.clazz).invk(registryKey.unwrap());
    }

    public static ResourceOrTagArgument$ResultWrapper inst(Object instance) {
        return new ResourceOrTagArgument$ResultWrapper(instance);
    }
}
