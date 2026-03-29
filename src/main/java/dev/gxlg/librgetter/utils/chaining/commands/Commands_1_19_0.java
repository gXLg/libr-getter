package dev.gxlg.librgetter.utils.chaining.commands;

import dev.gxlg.librgetter.commands.CommandsManager;
import dev.gxlg.versiont.gen.com.mojang.brigadier.arguments.ArgumentType;
import dev.gxlg.versiont.gen.com.mojang.brigadier.builder.ArgumentBuilder;
import dev.gxlg.versiont.gen.com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.gxlg.versiont.gen.net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import dev.gxlg.versiont.gen.net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallbackI;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

public class Commands_1_19_0 extends Commands_1_17_0 {
    @Override
    public void registerCommands(CommandsManager.Command callback) {
        ClientCommandRegistrationCallbackI fabricCallback = callback::register;
        ClientCommandRegistrationCallback.EVENT.register(fabricCallback.unwrap(ClientCommandRegistrationCallback.class));
    }

    @Override
    public LiteralArgumentBuilder literal(String command) {
        return ClientCommandManager.literal(command);
    }

    @Override
    public ArgumentBuilder argument(String command, ArgumentType argumentType) {
        return ClientCommandManager.argument(command, argumentType);
    }
}
