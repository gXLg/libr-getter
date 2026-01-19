package dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer;

import dev.gxlg.multiversion.R;

public class ClientPacketListenerWrapper extends R.RWrapper<ClientPacketListenerWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_634/net.minecraft.client.multiplayer.ClientPacketListener");

    protected ClientPacketListenerWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public net.minecraft.network.Connection getConnection(){
        return (net.minecraft.network.Connection) this.instance.mthd("method_48296/method_2872/getConnection").invk();
    }

    public static ClientPacketListenerWrapper inst(Object instance) {
        return new ClientPacketListenerWrapper(instance);
    }
}