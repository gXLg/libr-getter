package dev.gxlg.librgetter.utils.reflection.chaining.commands;

import com.mojang.brigadier.arguments.ArgumentType;
import dev.gxlg.multiversion.gen.com.mojang.brigadier.CommandDispatcherWrapper;
import dev.gxlg.multiversion.gen.com.mojang.brigadier.builder.ArgumentBuilderWrapper;
import dev.gxlg.multiversion.gen.com.mojang.brigadier.builder.LiteralArgumentBuilderWrapper;
import dev.gxlg.multiversion.gen.net.fabricmc.fabric.api.client.command.v2.ClientCommandManagerWrapper;
import dev.gxlg.multiversion.gen.net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallbackWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.commands.CommandBuildContextWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.commands.arguments.ResourceOrTagArgumentWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.core.registries.BuiltInRegistriesWrapper;

import java.lang.reflect.Proxy;

public class Commands_1_19_0 extends Commands_1_17_0 {
    @Override
    public void registerCommands() {
        Object listener = Proxy.newProxyInstance(
            Thread.currentThread().getContextClassLoader(), new Class[]{ ClientCommandRegistrationCallbackWrapper.clazz.self() }, (proxy, method, args) -> {
                if (method.getName().equals("register")) {
                    ArgumentType<?> enchantmentArgumentType = getEnchantmentArgumentType(CommandBuildContextWrapper.inst(args[1]));
                    registerLibrget(CommandDispatcherWrapper.inst(args[0]), enchantmentArgumentType);
                    return null;
                }
                return method.invoke(proxy, args);
            }
        );
        ClientCommandRegistrationCallbackWrapper.EVENT().register(listener);
    }

    protected ArgumentType<?> getEnchantmentArgumentType(CommandBuildContextWrapper context) {
        return ResourceOrTagArgumentWrapper.resourceOrTag(context, BuiltInRegistriesWrapper.ENCHANTMENT().key());
    }

    @Override
    protected LiteralArgumentBuilderWrapper literal(String command) {
        return ClientCommandManagerWrapper.literal(command);
    }

    @Override
    protected ArgumentBuilderWrapper argument(String command, ArgumentType<?> argumentType) {
        return ClientCommandManagerWrapper.argument(command, argumentType);
    }
}
