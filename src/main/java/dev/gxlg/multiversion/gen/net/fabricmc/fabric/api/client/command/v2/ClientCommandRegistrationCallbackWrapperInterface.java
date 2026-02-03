package dev.gxlg.multiversion.gen.net.fabricmc.fabric.api.client.command.v2;

import dev.gxlg.multiversion.R;

import java.lang.reflect.Proxy;

public interface ClientCommandRegistrationCallbackWrapperInterface extends R.RWrapperInterface<ClientCommandRegistrationCallbackWrapper> {
    void register(dev.gxlg.multiversion.gen.com.mojang.brigadier.CommandDispatcherWrapper dispatcher, dev.gxlg.multiversion.gen.net.minecraft.commands.CommandBuildContextWrapper context);

    @Override
    default ClientCommandRegistrationCallbackWrapper wrapper() {
        return ClientCommandRegistrationCallbackWrapper.inst(Proxy.newProxyInstance(
            Thread.currentThread().getContextClassLoader(), new Class[]{ ClientCommandRegistrationCallbackWrapper.clazz.self() }, (proxy, method, args) -> {
                String methodName = method.getName();
                if ((methodName.equals("register")) && R.methodMatches(method, void.class, dev.gxlg.multiversion.gen.com.mojang.brigadier.CommandDispatcherWrapper.clazz.self(), dev.gxlg.multiversion.gen.net.minecraft.commands.CommandBuildContextWrapper.clazz.self())) {
                    this.register(dev.gxlg.multiversion.gen.com.mojang.brigadier.CommandDispatcherWrapper.inst(args[0]), dev.gxlg.multiversion.gen.net.minecraft.commands.CommandBuildContextWrapper.inst(args[1]));
                    return null;
                }
                return method.invoke(proxy, args);
            }
        ));
    }
}