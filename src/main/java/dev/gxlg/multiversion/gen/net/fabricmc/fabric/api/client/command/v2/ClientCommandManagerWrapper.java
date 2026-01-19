package dev.gxlg.multiversion.gen.net.fabricmc.fabric.api.client.command.v2;

import dev.gxlg.multiversion.R;

public class ClientCommandManagerWrapper extends R.RWrapper<ClientCommandManagerWrapper> {
    public static final R.RClass clazz = R.clz("net.fabricmc.fabric.api.client.command.v2.ClientCommandManager");

    protected ClientCommandManagerWrapper(Object instance) {
        super(instance);
        R.RInstance rInstance = clazz.inst(instance);
    }

    public static ClientCommandManagerWrapper inst(Object instance) {
        return new ClientCommandManagerWrapper(instance);
    }

    public static dev.gxlg.multiversion.gen.com.mojang.brigadier.builder.LiteralArgumentBuilderWrapper literal(String name){
        return dev.gxlg.multiversion.gen.com.mojang.brigadier.builder.LiteralArgumentBuilderWrapper.inst(clazz.mthd("literal", String.class).invk(name));
    }

    public static dev.gxlg.multiversion.gen.com.mojang.brigadier.builder.ArgumentBuilderWrapper argument(String name, com.mojang.brigadier.arguments.ArgumentType argumentType){
        return dev.gxlg.multiversion.gen.com.mojang.brigadier.builder.ArgumentBuilderWrapper.inst(clazz.mthd("argument", String.class, com.mojang.brigadier.arguments.ArgumentType.class).invk(name, argumentType));
    }
}