package dev.gxlg.librgetter.utils.chaining.commands;

import com.mojang.brigadier.context.CommandContext;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.multiversion.V;
import dev.gxlg.multiversion.gen.net.minecraft.world.item.enchantment.EnchantmentWrapper;

import java.util.List;

public abstract class Commands {
    public abstract List<EnchantmentWrapper> getEnchantmentsFromCommandContext(CommandContext<?> context) throws LibrGetterException;

    public abstract String getCustomEnchantmentFromCommandContext(CommandContext<?> context);

    public abstract void registerCommands();

    private static Commands implementation = null;

    public static Commands getImpl() {
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
}
