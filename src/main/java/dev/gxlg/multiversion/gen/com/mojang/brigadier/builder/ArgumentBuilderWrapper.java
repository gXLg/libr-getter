package dev.gxlg.multiversion.gen.com.mojang.brigadier.builder;

import dev.gxlg.multiversion.R;

public class ArgumentBuilderWrapper extends R.RWrapper<ArgumentBuilderWrapper> {
    public static final R.RClass clazz = R.clz("com.mojang.brigadier.builder.ArgumentBuilder");

    protected ArgumentBuilderWrapper(Object instance) {
        super(instance);
        R.RInstance rInstance = clazz.inst(instance);
    }

    public dev.gxlg.multiversion.gen.com.mojang.brigadier.builder.ArgumentBuilderWrapper then(dev.gxlg.multiversion.gen.com.mojang.brigadier.builder.ArgumentBuilderWrapper argument){
        return dev.gxlg.multiversion.gen.com.mojang.brigadier.builder.ArgumentBuilderWrapper.inst(clazz.inst(this.instance).mthd("then", dev.gxlg.multiversion.gen.com.mojang.brigadier.builder.ArgumentBuilderWrapper.clazz).invk(argument.unwrap()));
    }

    public dev.gxlg.multiversion.gen.com.mojang.brigadier.builder.ArgumentBuilderWrapper executes(com.mojang.brigadier.Command command){
        return dev.gxlg.multiversion.gen.com.mojang.brigadier.builder.ArgumentBuilderWrapper.inst(clazz.inst(this.instance).mthd("executes", com.mojang.brigadier.Command.class).invk(command));
    }

    public static ArgumentBuilderWrapper inst(Object instance) {
        return new ArgumentBuilderWrapper(instance);
    }
}