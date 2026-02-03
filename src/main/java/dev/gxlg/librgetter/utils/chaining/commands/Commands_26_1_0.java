package dev.gxlg.librgetter.utils.chaining.commands;

import dev.gxlg.multiversion.gen.com.mojang.brigadier.arguments.ArgumentTypeWrapper;
import dev.gxlg.multiversion.gen.com.mojang.brigadier.builder.ArgumentBuilderWrapper;
import dev.gxlg.multiversion.gen.com.mojang.brigadier.builder.LiteralArgumentBuilderWrapper;
import dev.gxlg.multiversion.gen.net.fabricmc.fabric.api.client.command.v2.ClientCommandsWrapper;

public class Commands_26_1_0 extends Commands_1_21_0 {
    @Override
    protected LiteralArgumentBuilderWrapper literal(String command) {
        return ClientCommandsWrapper.literal(command);
    }

    @Override
    protected ArgumentBuilderWrapper argument(String command, ArgumentTypeWrapper argumentType) {
        return ClientCommandsWrapper.argument(command, argumentType);
    }

}
