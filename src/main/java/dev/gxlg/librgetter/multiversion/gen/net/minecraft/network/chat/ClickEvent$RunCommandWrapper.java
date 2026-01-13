package dev.gxlg.librgetter.multiversion.gen.net.minecraft.network.chat;

import dev.gxlg.librgetter.multiversion.R;

public class ClickEvent$RunCommandWrapper extends R.RWrapper<ClickEvent$RunCommandWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_2558$class_10609/net.minecraft.network.chat.ClickEvent$RunCommand");

    public ClickEvent$RunCommandWrapper(String command) {
        this(clazz.constr(String.class).newInst(command).self());
    }

    protected ClickEvent$RunCommandWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public static ClickEvent$RunCommandWrapper inst(Object instance) {
        return new ClickEvent$RunCommandWrapper(instance);
    }
}
