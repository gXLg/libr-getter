package dev.gxlg.multiversion.gen.net.minecraft.commands;

import dev.gxlg.multiversion.R;

public class CommandBuildContextWrapper extends R.RWrapper<CommandBuildContextWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_7157/net.minecraft.commands.CommandBuildContext");

    protected CommandBuildContextWrapper(Object instance) {
        super(instance);
        R.RInstance rInstance = clazz.inst(instance);
    }

    public static CommandBuildContextWrapper inst(Object instance) {
        return new CommandBuildContextWrapper(instance);
    }
}