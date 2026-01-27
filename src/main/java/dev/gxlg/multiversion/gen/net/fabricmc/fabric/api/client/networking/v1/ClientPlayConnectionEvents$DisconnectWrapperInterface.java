package dev.gxlg.multiversion.gen.net.fabricmc.fabric.api.client.networking.v1;

import dev.gxlg.multiversion.R;

import java.lang.reflect.Proxy;

public interface ClientPlayConnectionEvents$DisconnectWrapperInterface extends R.RWrapperInterface<ClientPlayConnectionEvents$DisconnectWrapper> {
    void onPlayDisconnect(dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.ClientPacketListenerWrapper handler, dev.gxlg.multiversion.gen.net.minecraft.client.MinecraftWrapper client);

    @Override
    default ClientPlayConnectionEvents$DisconnectWrapper wrapper() {
        return ClientPlayConnectionEvents$DisconnectWrapper.inst(Proxy.newProxyInstance(
            Thread.currentThread().getContextClassLoader(), new Class[]{ ClientPlayConnectionEvents$DisconnectWrapper.clazz.self() }, (proxy, method, args) -> {
                String methodName = method.getName();
                if ((methodName.equals("onPlayDisconnect")) && R.methodMatches(method, void.class, dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.ClientPacketListenerWrapper.clazz, dev.gxlg.multiversion.gen.net.minecraft.client.MinecraftWrapper.clazz)) {
                    this.onPlayDisconnect(dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.ClientPacketListenerWrapper.inst(args[0]), dev.gxlg.multiversion.gen.net.minecraft.client.MinecraftWrapper.inst(args[1]));
                    return null;
                }
                return method.invoke(proxy, args);
            }
        ));
    }
}