package dev.gxlg.multiversion.gen.com.mojang.brigadier;

import dev.gxlg.multiversion.R;

public class CommandDispatcherWrapper extends R.RWrapper<CommandDispatcherWrapper> {
    public static final R.RClass clazz = R.clz("com.mojang.brigadier.CommandDispatcher");

    protected CommandDispatcherWrapper(Object instance) {
        super(instance);
        R.RInstance rInstance = clazz.inst(instance);
    }

    public void register(dev.gxlg.multiversion.gen.com.mojang.brigadier.builder.LiteralArgumentBuilderWrapper command){
        clazz.inst(this.instance).mthd("register", dev.gxlg.multiversion.gen.com.mojang.brigadier.builder.LiteralArgumentBuilderWrapper.clazz).invk(command.unwrap());
    }

    public static CommandDispatcherWrapper inst(Object instance) {
        return new CommandDispatcherWrapper(instance);
    }
}