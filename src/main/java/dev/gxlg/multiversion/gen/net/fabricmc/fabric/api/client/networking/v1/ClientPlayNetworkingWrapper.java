package dev.gxlg.multiversion.gen.net.fabricmc.fabric.api.client.networking.v1;

import dev.gxlg.multiversion.R;

public class ClientPlayNetworkingWrapper extends R.RWrapper<ClientPlayNetworkingWrapper> {
    public static final R.RClass clazz = R.clz("net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking");

    protected ClientPlayNetworkingWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public static ClientPlayNetworkingWrapper inst(Object instance) {
        return new ClientPlayNetworkingWrapper(instance);
    }

    public static void send(dev.gxlg.multiversion.gen.net.minecraft.network.protocol.common.custom.CustomPacketPayloadWrapper customPayload){
        clazz.mthd("send", dev.gxlg.multiversion.gen.net.minecraft.network.protocol.common.custom.CustomPacketPayloadWrapper.clazz).invk(customPayload.unwrap());
    }
}