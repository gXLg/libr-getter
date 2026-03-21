package dev.gxlg.librgetter.utils.chaining.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.command.CommandHelper;
import dev.gxlg.librgetter.command.LibrGetCommand;
import dev.gxlg.librgetter.utils.types.config.helpers.Configurable;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.gen.com.mojang.brigadier.CommandDispatcher;
import dev.gxlg.versiont.gen.com.mojang.brigadier.arguments.ArgumentType;
import dev.gxlg.versiont.gen.com.mojang.brigadier.builder.ArgumentBuilder;
import dev.gxlg.versiont.gen.com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.gxlg.versiont.gen.com.mojang.brigadier.context.CommandContext;
import dev.gxlg.versiont.gen.net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
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
    public void registerCommands() {
        registerLibrget(ClientCommandManager.DISPATCHER(), ItemEnchantmentArgument.enchantment());
    }

    protected void registerLibrget(CommandDispatcher dispatcher, ArgumentType enchantmentArgumentType) {
        ArgumentBuilder baseCommand = literal("librget");

        ArgumentBuilder subCommand;

        // add subcommand
        {
            subCommand = literal("add");

            ArgumentBuilder enchantmentArgument, levelArgument, priceArgument;

            enchantmentArgument = argument("enchantment", enchantmentArgumentType).executes(CommandHelper.commandWrapper(LibrGetCommand::add));
            levelArgument = argument("level", IntegerArgumentType.integer(1)).executes(CommandHelper.commandWrapper(LibrGetCommand::add));
            priceArgument = argument("maxprice", IntegerArgumentType.integer(1, 64)).executes(CommandHelper.commandWrapper(LibrGetCommand::add));
            subCommand = subCommand.then(enchantmentArgument.then(levelArgument.then(priceArgument)));

            enchantmentArgument = argument("enchantment_custom", StringArgumentType.string());
            levelArgument = argument("level", IntegerArgumentType.integer(1)).executes(CommandHelper.commandWrapper(LibrGetCommand::addCustom));
            priceArgument = argument("maxprice", IntegerArgumentType.integer(1, 64)).executes(CommandHelper.commandWrapper(LibrGetCommand::addCustom));
            subCommand = subCommand.then(enchantmentArgument.then(levelArgument.then(priceArgument)));

            baseCommand = baseCommand.then(subCommand);
        }

        // remove subcommand
        {
            subCommand = literal("remove");
            ArgumentBuilder enchantmentArgument, levelArgument;

            enchantmentArgument = argument("enchantment", enchantmentArgumentType).executes(CommandHelper.commandWrapper(LibrGetCommand::remove));
            levelArgument = argument("level", IntegerArgumentType.integer(1)).executes(CommandHelper.commandWrapper(LibrGetCommand::remove));
            subCommand = subCommand.then(enchantmentArgument.then(levelArgument));

            enchantmentArgument = argument("enchantment_custom", StringArgumentType.string()).executes(CommandHelper.commandWrapper(LibrGetCommand::removeCustom));
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

                ArgumentBuilder configArgument = literal(name).executes(CommandHelper.commandWrapper(ctx -> LibrGetCommand.config(ctx, configurable)));
                ArgumentBuilder valueArgument = argument("value", configurable.commandArgument()).executes(CommandHelper.commandWrapper(ctx -> LibrGetCommand.config(ctx, configurable)));

                subCommand = subCommand.then(configArgument.then(valueArgument));
            }
            baseCommand = baseCommand.then(subCommand);
        }

        // selector
        {
            baseCommand = baseCommand.executes(CommandHelper.commandWrapper(ctx -> LibrGetCommand.selector()));
        }

        dispatcher.register((LiteralArgumentBuilder) baseCommand);
    }

    protected LiteralArgumentBuilder literal(String command) {
        return ClientCommandManager.literal(command);
    }

    protected ArgumentBuilder argument(String command, ArgumentType argumentType) {
        return ClientCommandManager.argument(command, argumentType);
    }

    protected ArgumentBuilder argument(String command, com.mojang.brigadier.arguments.ArgumentType<?> argumentType) {
        return argument(command, R.wrapperInst(ArgumentType.class, argumentType));
    }
}
