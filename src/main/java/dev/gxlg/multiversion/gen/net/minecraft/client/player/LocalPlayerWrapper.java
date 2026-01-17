package dev.gxlg.multiversion.gen.net.minecraft.client.player;

import dev.gxlg.multiversion.R;

public class LocalPlayerWrapper extends R.RWrapper<LocalPlayerWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_746/net.minecraft.client.player.LocalPlayer");

    private final R.RField clientLevel;

    protected LocalPlayerWrapper(Object instance) {
        super(clazz.inst(instance));
        this.clientLevel = this.instance.fld("field_17892/clientLevel");
    }

    public void displayClientMessage(dev.gxlg.multiversion.gen.net.minecraft.network.chat.ComponentWrapper message, boolean actionBar){
        this.instance.mthd("method_7353/displayClientMessage", dev.gxlg.multiversion.gen.net.minecraft.network.chat.ComponentWrapper.clazz, boolean.class).invk(message.unwrap(), actionBar);
    }

    public net.minecraft.client.multiplayer.ClientLevel level(){
        return (net.minecraft.client.multiplayer.ClientLevel) this.instance.mthd("method_73183/level").invk();
    }

    public net.minecraft.client.multiplayer.ClientLevel getLevel(){
        return (net.minecraft.client.multiplayer.ClientLevel) this.instance.mthd("method_37908/getLevel").invk();
    }

    public net.minecraft.client.multiplayer.ClientLevel getClientLevel() {
        return (net.minecraft.client.multiplayer.ClientLevel) this.clientLevel.get();
    }
    
    public void setClientLevel(net.minecraft.client.multiplayer.ClientLevel value) {
        this.clientLevel.set(value);
    }

    public static LocalPlayerWrapper inst(Object instance) {
        return new LocalPlayerWrapper(instance);
    }
}