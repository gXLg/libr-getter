package dev.gxlg.librgetter.multiversion.gen.com.mojang.brigadier.builder;

import dev.gxlg.librgetter.multiversion.R;

public class ArgumentBuilderWrapper extends R.RWrapper<ArgumentBuilderWrapper> {
    public static final R.RClass clazz = R.clz("com.mojang.brigadier.builder.ArgumentBuilder");

    protected ArgumentBuilderWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public dev.gxlg.librgetter.multiversion.gen.com.mojang.brigadier.builder.ArgumentBuilderWrapper then(dev.gxlg.librgetter.multiversion.gen.com.mojang.brigadier.builder.ArgumentBuilderWrapper argument) {
         return dev.gxlg.librgetter.multiversion.gen.com.mojang.brigadier.builder.ArgumentBuilderWrapper.inst(instance.mthd("then", dev.gxlg.librgetter.multiversion.gen.com.mojang.brigadier.builder.ArgumentBuilderWrapper.clazz).invk(argument.unwrap()));
    }

    public dev.gxlg.librgetter.multiversion.gen.com.mojang.brigadier.builder.ArgumentBuilderWrapper executes(com.mojang.brigadier.Command command) {
         return dev.gxlg.librgetter.multiversion.gen.com.mojang.brigadier.builder.ArgumentBuilderWrapper.inst(instance.mthd("executes", com.mojang.brigadier.Command.class).invk(command));
    }

    public static ArgumentBuilderWrapper inst(Object instance) {
        return new ArgumentBuilderWrapper(instance);
    }
}
