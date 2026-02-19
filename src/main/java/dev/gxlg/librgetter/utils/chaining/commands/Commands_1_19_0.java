package dev.gxlg.librgetter.utils.chaining.commands;

import dev.gxlg.versiont.gen.com.mojang.brigadier.arguments.ArgumentType;
import dev.gxlg.versiont.gen.com.mojang.brigadier.builder.ArgumentBuilder;
import dev.gxlg.versiont.gen.com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.gxlg.versiont.gen.net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import dev.gxlg.versiont.gen.net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallbackI;
import dev.gxlg.versiont.gen.net.minecraft.commands.CommandBuildContext;
import dev.gxlg.versiont.gen.net.minecraft.commands.arguments.ResourceOrTagArgument;
import dev.gxlg.versiont.gen.net.minecraft.core.registries.BuiltInRegistries;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

public class Commands_1_19_0 extends Commands_1_17_0 {
    @Override
    public void registerCommands() {
        ClientCommandRegistrationCallbackI callback = (dispatcher, context) -> {
            ArgumentType enchantmentArgumentType = getEnchantmentArgumentType(context);
            registerLibrget(dispatcher, enchantmentArgumentType);
        };
        ClientCommandRegistrationCallback.EVENT.register(callback.unwrap(ClientCommandRegistrationCallback.class));
    }

    protected ArgumentType getEnchantmentArgumentType(CommandBuildContext context) {
        return ResourceOrTagArgument.resourceOrTag(context, BuiltInRegistries.ENCHANTMENT().key());
    }

    @Override
    protected LiteralArgumentBuilder literal(String command) {
        return ClientCommandManager.literal(command);
    }

    @Override
    protected ArgumentBuilder argument(String command, ArgumentType argumentType) {
        return ClientCommandManager.argument(command, argumentType);
    }
}
