package dev.gxlg.multiversion.gen.net.fabricmc.fabric.api.client.networking.v1;

import dev.gxlg.multiversion.R;

import java.lang.reflect.Proxy;

public interface ClientPlayConnectionEvents$JoinWrapperInterface extends R.RWrapperInterface<ClientPlayConnectionEvents$JoinWrapper> {
    void onPlayReady(dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.ClientPacketListenerWrapper handler, net.fabricmc.fabric.api.networking.v1.PacketSender sender, dev.gxlg.multiversion.gen.net.minecraft.client.MinecraftWrapper client);

    default Object unwrap() {
        return null;
    }

    @Override
    default ClientPlayConnectionEvents$JoinWrapper wrapper() {
        return ClientPlayConnectionEvents$JoinWrapper.inst(Proxy.newProxyInstance(
            Thread.currentThread().getContextClassLoader(), new Class[]{ ClientPlayConnectionEvents$JoinWrapper.clazz.self() }, (proxy, method, args) -> {
                String methodName = method.getName();
                if ((methodName.equals("onPlayReady")) && R.methodMatches(method, void.class, dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.ClientPacketListenerWrapper.clazz.self(), net.fabricmc.fabric.api.networking.v1.PacketSender.class, dev.gxlg.multiversion.gen.net.minecraft.client.MinecraftWrapper.clazz.self())) {
                    this.onPlayReady(dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.ClientPacketListenerWrapper.inst(args[0]), (net.fabricmc.fabric.api.networking.v1.PacketSender) args[1], dev.gxlg.multiversion.gen.net.minecraft.client.MinecraftWrapper.inst(args[2]));
                    return null;
                }
                return method.invoke(proxy, args);
            }
        ));
    }
}