package dev.gxlg.librgetter.utils.reflection.chaining.commands;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.command.CommandHelper;
import dev.gxlg.librgetter.command.LibrGetCommand;
import dev.gxlg.librgetter.utils.types.config.helpers.Configurable;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.multiversion.gen.com.mojang.brigadier.CommandDispatcherWrapper;
import dev.gxlg.multiversion.gen.com.mojang.brigadier.builder.ArgumentBuilderWrapper;
import dev.gxlg.multiversion.gen.com.mojang.brigadier.builder.LiteralArgumentBuilderWrapper;
import dev.gxlg.multiversion.gen.net.fabricmc.fabric.api.client.command.v1.ClientCommandManagerWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.commands.arguments.ItemEnchantmentArgumentWrapper;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.List;

public class Commands_1_17_0 extends Commands {
    @Override
    public List<Enchantment> getEnchantmentsFromCommandContext(CommandContext<?> context) throws LibrGetterException {
        Enchantment enchantment = context.getArgument("enchantment", Enchantment.class);
        return List.of(enchantment);
    }

    @Override
    public String getCustomEnchantmentFromCommandContext(CommandContext<?> context) {
        return context.getArgument("enchantment_custom", String.class);
    }

    @Override
    public void registerCommands() {
        registerLibrget(ClientCommandManagerWrapper.DISPATCHER(), ItemEnchantmentArgumentWrapper.enchantment());
    }

    protected void registerLibrget(CommandDispatcherWrapper dispatcher, ArgumentType<?> enchantmentArgumentType) {
        ArgumentBuilderWrapper baseCommand = literal("librget");

        ArgumentBuilderWrapper subCommand;

        // add subcommand
        {
            subCommand = literal("add");

            ArgumentBuilderWrapper enchantmentArgument, levelArgument, priceArgument;

            enchantmentArgument = argument("enchantment", enchantmentArgumentType).executes(CommandHelper.commandWrapper(LibrGetCommand::add));
            levelArgument = argument("level", IntegerArgumentType.integer(1)).executes(CommandHelper.commandWrapper(LibrGetCommand::add));
            priceArgument = argument("maxprice", IntegerArgumentType.integer(1, 64)).executes(CommandHelper.commandWrapper(LibrGetCommand::add));
            subCommand = subCommand.then(enchantmentArgument.then(levelArgument).then(priceArgument));

            enchantmentArgument = argument("enchantment_custom", enchantmentArgumentType);
            levelArgument = argument("level", IntegerArgumentType.integer(1));
            priceArgument = argument("maxprice", IntegerArgumentType.integer(1, 64)).executes(CommandHelper.commandWrapper(LibrGetCommand::addCustom));
            subCommand = subCommand.then(enchantmentArgument.then(levelArgument).then(priceArgument));

            baseCommand = baseCommand.then(subCommand);
        }

        // remove subcommand
        {
            subCommand = literal("remove");
            ArgumentBuilderWrapper enchantmentArgument, levelArgument;

            enchantmentArgument = argument("enchantment", enchantmentArgumentType).executes(CommandHelper.commandWrapper(LibrGetCommand::remove));
            levelArgument = argument("level", IntegerArgumentType.integer(1)).executes(CommandHelper.commandWrapper(LibrGetCommand::remove));
            subCommand = subCommand.then(enchantmentArgument.then(levelArgument));

            enchantmentArgument = argument("enchantment", enchantmentArgumentType).executes(CommandHelper.commandWrapper(LibrGetCommand::removeCustom));
            levelArgument = argument("level", IntegerArgumentType.integer(1)).executes(CommandHelper.commandWrapper(LibrGetCommand::removeCustom));
            subCommand = subCommand.then(enchantmentArgument.then(levelArgument));

            baseCommand = baseCommand.then(subCommand);
        }

        // no-arg subcommands
        {
            subCommand = literal("clear").executes(CommandHelper.commandWrapper(ctx -> LibrGetCommand.clearGoals()));
            baseCommand = baseCommand.then(subCommand);

            subCommand = literal("list").executes(CommandHelper.commandWrapper(ctx -> LibrGetCommand.list()));
            baseCommand = baseCommand.then(subCommand);

            subCommand = literal("stop").executes(CommandHelper.commandWrapper(ctx -> LibrGetCommand.stopWorking()));
            baseCommand = baseCommand.then(subCommand);

            subCommand = literal("start").executes(CommandHelper.commandWrapper(ctx -> LibrGetCommand.startWorking()));
            baseCommand = baseCommand.then(subCommand);

            subCommand = literal("continue").executes(CommandHelper.commandWrapper(ctx -> LibrGetCommand.continueWorking()));
            baseCommand = baseCommand.then(subCommand);

            subCommand = literal("auto").executes(CommandHelper.commandWrapper(ctx -> LibrGetCommand.autostart()));
            baseCommand = baseCommand.then(subCommand);
        }

        // automatically create config commands for each simply configurable value in Config
        {
            subCommand = literal("config");
            for (Configurable<?> configurable : LibrGetter.configManager.getConfigurables()) {
                String name = configurable.name();

                ArgumentBuilderWrapper configArgument = literal(name).executes(CommandHelper.commandWrapper(ctx -> LibrGetCommand.config(ctx, configurable)));
                ArgumentBuilderWrapper valueArgument = argument("value", configurable.commandArgument()).executes(CommandHelper.commandWrapper(ctx -> LibrGetCommand.config(ctx, configurable)));

                subCommand = subCommand.then(configArgument.then(valueArgument));
            }
            baseCommand = baseCommand.then(subCommand);
        }

        // selector
        {
            baseCommand = baseCommand.executes(CommandHelper.commandWrapper(ctx -> LibrGetCommand.selector()));
        }

        dispatcher.register(baseCommand.downcast(LiteralArgumentBuilderWrapper.class));
    }

    protected LiteralArgumentBuilderWrapper literal(String command) {
        return ClientCommandManagerWrapper.literal(command);
    }

    protected ArgumentBuilderWrapper argument(String command, ArgumentType<?> argumentType) {
        return ClientCommandManagerWrapper.argument(command, argumentType);
    }
}
