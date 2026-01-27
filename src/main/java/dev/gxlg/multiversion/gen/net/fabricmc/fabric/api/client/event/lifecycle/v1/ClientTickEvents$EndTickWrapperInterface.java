package dev.gxlg.multiversion.gen.net.fabricmc.fabric.api.client.event.lifecycle.v1;

import dev.gxlg.multiversion.R;

import java.lang.reflect.Proxy;

public interface ClientTickEvents$EndTickWrapperInterface extends R.RWrapperInterface<ClientTickEvents$EndTickWrapper> {
    void onEndTick(dev.gxlg.multiversion.gen.net.minecraft.client.MinecraftWrapper client);

    @Override
    default ClientTickEvents$EndTickWrapper wrapper() {
        return ClientTickEvents$EndTickWrapper.inst(Proxy.newProxyInstance(
            Thread.currentThread().getContextClassLoader(), new Class[]{ ClientTickEvents$EndTickWrapper.clazz.self() }, (proxy, method, args) -> {
                String methodName = method.getName();
                if ((methodName.equals("onEndTick")) && R.methodMatches(method, void.class, dev.gxlg.multiversion.gen.net.minecraft.client.MinecraftWrapper.clazz)) {
                    this.onEndTick(dev.gxlg.multiversion.gen.net.minecraft.client.MinecraftWrapper.inst(args[0]));
                    return null;
                }
                return method.invoke(proxy, args);
            }
        ));
    }
}