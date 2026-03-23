package dev.gxlg.librgetter.utils.chaining.enchantments;

import dev.gxlg.librgetter.utils.types.exceptions.librgetter.common.InternalErrorException;
import dev.gxlg.versiont.api.V;
import dev.gxlg.versiont.gen.net.minecraft.resources.Identifier;
import dev.gxlg.versiont.gen.net.minecraft.world.item.ItemStack;
import dev.gxlg.versiont.gen.net.minecraft.world.item.enchantment.Enchantment;

public class Enchantments {
    private static final Base implementation;

    static {
        if (V.lower("1.19.3")) {
            implementation = new Enchantments_1_17_0();
        } else if (V.lower("1.21")) {
            implementation = new Enchantments_1_19_3();
        } else {
            implementation = new Enchantments_1_21_0();
        }
    }

    public static Identifier enchantmentId(Enchantment enchantment) {
        return implementation.enchantmentId(enchantment);
    }

    public static int getEfficiencyLevel(ItemStack stack) {
        return implementation.getEfficiencyLevel(stack);
    }

    public static boolean canBeTraded(Enchantment enchantment) throws InternalErrorException {
        return implementation.canBeTraded(enchantment);
    }

    public abstract static class Base {
        public abstract Identifier enchantmentId(Enchantment enchantment);

        public abstract int getEfficiencyLevel(ItemStack stack);

        public abstract boolean canBeTraded(Enchantment enchantment) throws InternalErrorException;
    }
}
