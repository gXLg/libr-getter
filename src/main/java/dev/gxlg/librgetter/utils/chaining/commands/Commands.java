package dev.gxlg.librgetter.utils.chaining.commands;

import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.versiont.api.V;
import dev.gxlg.versiont.gen.com.mojang.brigadier.context.CommandContext;
import dev.gxlg.versiont.gen.net.minecraft.world.item.enchantment.Enchantment;

import java.util.List;

public class Commands {
    private static Base implementation = null;

    public static List<Enchantment> getEnchantmentsFromCommandContext(CommandContext context) throws LibrGetterException {
        return getImpl().getEnchantmentsFromCommandContext(context);
    }

    public static String getCustomEnchantmentFromCommandContext(CommandContext context) {
        return getImpl().getCustomEnchantmentFromCommandContext(context);
    }

    public static void registerCommands() {
        getImpl().registerCommands();
    }

    private static Base getImpl() {
        if (implementation != null) {
            return implementation;
        }
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
        return implementation;
    }

    public abstract static class Base {
        public abstract List<Enchantment> getEnchantmentsFromCommandContext(CommandContext context) throws LibrGetterException;

        public abstract String getCustomEnchantmentFromCommandContext(CommandContext context);

        public abstract void registerCommands();
    }
}
