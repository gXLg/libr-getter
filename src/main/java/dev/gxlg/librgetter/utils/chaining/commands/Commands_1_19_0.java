package dev.gxlg.librgetter.utils.chaining.commands;

import com.mojang.brigadier.arguments.ArgumentType;
import dev.gxlg.multiversion.gen.com.mojang.brigadier.builder.ArgumentBuilderWrapper;
import dev.gxlg.multiversion.gen.com.mojang.brigadier.builder.LiteralArgumentBuilderWrapper;
import dev.gxlg.multiversion.gen.net.fabricmc.fabric.api.client.command.v2.ClientCommandManagerWrapper;
import dev.gxlg.multiversion.gen.net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallbackWrapper;
import dev.gxlg.multiversion.gen.net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallbackWrapperInterface;
import dev.gxlg.multiversion.gen.net.minecraft.commands.CommandBuildContextWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.commands.arguments.ResourceOrTagArgumentWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.core.registries.BuiltInRegistriesWrapper;

public class Commands_1_19_0 extends Commands_1_17_0 {
    @Override
    public void registerCommands() {
        ClientCommandRegistrationCallbackWrapperInterface listener = (dispatcher, context) -> {
            ArgumentType<?> enchantmentArgumentType = getEnchantmentArgumentType(context);
            registerLibrget(dispatcher, enchantmentArgumentType);
        };
        ClientCommandRegistrationCallbackWrapper.EVENT().register(listener.wrapper().unwrap());
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
