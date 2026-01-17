package dev.gxlg.multiversion.gen.net.minecraft.client.player;

import dev.gxlg.multiversion.R;

public class LocalPlayerWrapper extends R.RWrapper<LocalPlayerWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_746/net.minecraft.client.player.LocalPlayer");

    protected LocalPlayerWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public void displayClientMessage(dev.gxlg.multiversion.gen.net.minecraft.network.chat.ComponentWrapper message, boolean actionBar){
        this.instance.mthd("method_7353/displayClientMessage", dev.gxlg.multiversion.gen.net.minecraft.network.chat.ComponentWrapper.clazz, boolean.class).invk(message.unwrap(), actionBar);
    }

    public static LocalPlayerWrapper inst(Object instance) {
        return new LocalPlayerWrapper(instance);
    }
}