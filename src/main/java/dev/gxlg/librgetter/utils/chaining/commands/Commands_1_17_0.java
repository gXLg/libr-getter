package dev.gxlg.librgetter.utils.chaining.commands;

import dev.gxlg.librgetter.commands.CommandsManager;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.gen.com.mojang.brigadier.arguments.ArgumentType;
import dev.gxlg.versiont.gen.com.mojang.brigadier.builder.ArgumentBuilder;
import dev.gxlg.versiont.gen.com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.gxlg.versiont.gen.com.mojang.brigadier.context.CommandContext;
import dev.gxlg.versiont.gen.net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import dev.gxlg.versiont.gen.net.minecraft.commands.CommandBuildContext;
import dev.gxlg.versiont.gen.net.minecraft.commands.arguments.ItemEnchantmentArgument;
import dev.gxlg.versiont.gen.net.minecraft.world.item.enchantment.Enchantment;

import java.util.List;

public class Commands_1_17_0 extends Commands.Base {
    @Override
    public List<Enchantment> getEnchantmentsFromCommandContext(CommandContext context) throws LibrGetterException {
        Enchantment enchantment = (Enchantment) context.getArgument("enchantment", Enchantment.clazz);
        return List.of(enchantment);
    }

    @Override
    public String getCustomEnchantmentFromCommandContext(CommandContext context) {
        return context.getArgument("enchantment_custom", R.clz(String.class)).unwrap(String.class);
    }

    @Override
    public void registerCommands(CommandsManager.Command callback) {
        callback.register(ClientCommandManager.DISPATCHER(), null);
    }

    @Override
    public ArgumentType getEnchantmentArgumentType(CommandBuildContext context) {
        return ItemEnchantmentArgument.enchantment();
    }

    @Override
    public LiteralArgumentBuilder literal(String command) {
        return ClientCommandManager.literal(command);
    }

    @Override
    public ArgumentBuilder argument(String command, ArgumentType argumentType) {
        return ClientCommandManager.argument(command, argumentType);
    }

    @Override
    public ArgumentBuilder argument(String command, com.mojang.brigadier.arguments.ArgumentType<?> argumentType) {
        return argument(command, R.wrapperInst(ArgumentType.class, argumentType));
    }
}
