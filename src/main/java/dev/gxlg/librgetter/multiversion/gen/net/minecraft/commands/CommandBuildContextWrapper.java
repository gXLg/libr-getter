package dev.gxlg.librgetter.multiversion.gen.net.minecraft.commands;

import dev.gxlg.librgetter.multiversion.R;

public class CommandBuildContextWrapper extends R.RWrapper<CommandBuildContextWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_7157/net.minecraft.commands.CommandBuildContext");

    protected CommandBuildContextWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public static CommandBuildContextWrapper inst(Object instance) {
        return new CommandBuildContextWrapper(instance);
    }
}
