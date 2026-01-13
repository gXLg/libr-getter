package dev.gxlg.librgetter.multiversion.gen.com.mojang.brigadier;

import dev.gxlg.librgetter.multiversion.R;

public class CommandDispatcherWrapper extends R.RWrapper<CommandDispatcherWrapper> {
    public static final R.RClass clazz = R.clz("com.mojang.brigadier.CommandDispatcher");

    protected CommandDispatcherWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public void register(dev.gxlg.librgetter.multiversion.gen.com.mojang.brigadier.builder.LiteralArgumentBuilderWrapper command) {
         instance.mthd("register", dev.gxlg.librgetter.multiversion.gen.com.mojang.brigadier.builder.LiteralArgumentBuilderWrapper.clazz).invk(command.unwrap());
    }

    public static CommandDispatcherWrapper inst(Object instance) {
        return new CommandDispatcherWrapper(instance);
    }
}
