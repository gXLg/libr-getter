package dev.gxlg.librgetter.utils.chaining.commands;

import dev.gxlg.librgetter.commands.CommandsManager;
import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.versiont.api.V;
import dev.gxlg.versiont.gen.com.mojang.brigadier.arguments.ArgumentType;
import dev.gxlg.versiont.gen.com.mojang.brigadier.builder.ArgumentBuilder;
import dev.gxlg.versiont.gen.com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.gxlg.versiont.gen.com.mojang.brigadier.context.CommandContext;
import dev.gxlg.versiont.gen.net.minecraft.commands.CommandBuildContext;
import dev.gxlg.versiont.gen.net.minecraft.world.item.enchantment.Enchantment;

import java.util.List;

public class Commands {
    private static final Base implementation;

    static {
        if (V.lower("1.19")) {
            implementation = new Commands_1_17_0();
        } else if (V.lower("1.19.3")) {
            implementation = new Commands_1_19_0();
        } else if (V.lower("1.21")) {
            implementation = new Commands_1_19_3();
        } else if (V.lower("26.1")) {
            implementation = new Commands_1_21_0();
        } else {
            implementation = new Commands_26_1_0();
        }
    }

    public static List<Enchantment> getEnchantmentsFromCommandContext(CommandContext context) throws LibrGetterException {
        return implementation.getEnchantmentsFromCommandContext(context);
    }

    public static String getCustomEnchantmentFromCommandContext(CommandContext context) {
        return implementation.getCustomEnchantmentFromCommandContext(context);
    }

    public static void registerCommands(CommandsManager.Command callback) {
        implementation.registerCommands(callback);
    }

    public static ArgumentType getEnchantmentArgumentType(CommandBuildContext context) {
        return implementation.getEnchantmentArgumentType(context);
    }

    public static LiteralArgumentBuilder literal(String command) {
        return implementation.literal(command);
    }

    public static ArgumentBuilder argument(String command, ArgumentType argumentType) {
        return implementation.argument(command, argumentType);
    }

    public static ArgumentBuilder argument(String command, com.mojang.brigadier.arguments.ArgumentType<?> argumentType) {
        return implementation.argument(command, argumentType);
    }

    public abstract static class Base {
        public abstract List<Enchantment> getEnchantmentsFromCommandContext(CommandContext context) throws LibrGetterException;

        public abstract String getCustomEnchantmentFromCommandContext(CommandContext context);

        public abstract void registerCommands(CommandsManager.Command callback);

        public abstract ArgumentType getEnchantmentArgumentType(CommandBuildContext context);

        public abstract LiteralArgumentBuilder literal(String command);

        public abstract ArgumentBuilder argument(String command, ArgumentType argumentType);

        public abstract ArgumentBuilder argument(String command, com.mojang.brigadier.arguments.ArgumentType<?> argumentType);
    }
}
