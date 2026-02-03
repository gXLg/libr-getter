package dev.gxlg.librgetter.utils.chaining.commands;

import dev.gxlg.multiversion.gen.com.mojang.brigadier.arguments.ArgumentTypeWrapper;
import dev.gxlg.multiversion.gen.com.mojang.brigadier.builder.ArgumentBuilderWrapper;
import dev.gxlg.multiversion.gen.com.mojang.brigadier.builder.LiteralArgumentBuilderWrapper;
import dev.gxlg.multiversion.gen.net.fabricmc.fabric.api.client.command.v2.ClientCommandManagerWrapper;
import dev.gxlg.multiversion.gen.net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallbackWrapperInterface;
import dev.gxlg.multiversion.gen.net.minecraft.commands.CommandBuildContextWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.commands.arguments.ResourceOrTagArgumentWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.core.registries.BuiltInRegistriesWrapper;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

public class Commands_1_19_0 extends Commands_1_17_0 {
    @Override
    public void registerCommands() {
        ClientCommandRegistrationCallback.EVENT.register((
                                                             (ClientCommandRegistrationCallbackWrapperInterface) (dispatcher, context) -> {
                                                                 ArgumentTypeWrapper enchantmentArgumentType = getEnchantmentArgumentType(context);
                                                                 registerLibrget(dispatcher, enchantmentArgumentType);
                                                             }
                                                         ).wrapper().unwrap(ClientCommandRegistrationCallback.class));
    }

    protected ArgumentTypeWrapper getEnchantmentArgumentType(CommandBuildContextWrapper context) {
        return ResourceOrTagArgumentWrapper.resourceOrTag(context, BuiltInRegistriesWrapper.ENCHANTMENT().key());
    }

    @Override
    protected LiteralArgumentBuilderWrapper literal(String command) {
        return ClientCommandManagerWrapper.literal(command);
    }

    @Override
    protected ArgumentBuilderWrapper argument(String command, ArgumentTypeWrapper argumentType) {
        return ClientCommandManagerWrapper.argument(command, argumentType);
    }
}
