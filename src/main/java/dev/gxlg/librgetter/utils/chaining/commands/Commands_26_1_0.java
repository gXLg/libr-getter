package dev.gxlg.librgetter.utils.chaining.commands;

import dev.gxlg.versiont.gen.com.mojang.brigadier.arguments.ArgumentType;
import dev.gxlg.versiont.gen.com.mojang.brigadier.builder.ArgumentBuilder;
import dev.gxlg.versiont.gen.com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.gxlg.versiont.gen.net.fabricmc.fabric.api.client.command.v2.ClientCommands;

public class Commands_26_1_0 extends Commands_1_21_0 {
    @Override
    protected LiteralArgumentBuilder literal(String command) {
        return ClientCommands.literal(command);
    }

    @Override
    protected ArgumentBuilder argument(String command, ArgumentType argumentType) {
        return ClientCommands.argument(command, argumentType);
    }

}
