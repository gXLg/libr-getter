package dev.gxlg.librgetter.multiversion.gen.net.minecraft.commands.arguments;

import dev.gxlg.librgetter.multiversion.R;

public class ResourceOrTagArgumentWrapper extends R.RWrapper<ResourceOrTagArgumentWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_7737/net.minecraft.commands.arguments.ResourceOrTagArgument");

    protected ResourceOrTagArgumentWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public static ResourceOrTagArgumentWrapper inst(Object instance) {
        return new ResourceOrTagArgumentWrapper(instance);
    }

    public static com.mojang.brigadier.arguments.ArgumentType resourceOrTag(dev.gxlg.librgetter.multiversion.gen.net.minecraft.commands.CommandBuildContextWrapper context, dev.gxlg.librgetter.multiversion.gen.net.minecraft.resources.ResourceKeyWrapper registryKey) {
         return (com.mojang.brigadier.arguments.ArgumentType) clazz.mthd("method_45637/resourceOrTag", dev.gxlg.librgetter.multiversion.gen.net.minecraft.commands.CommandBuildContextWrapper.clazz, dev.gxlg.librgetter.multiversion.gen.net.minecraft.resources.ResourceKeyWrapper.clazz).invk(context.unwrap(), registryKey.unwrap());
    }
}
