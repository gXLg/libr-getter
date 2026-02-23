package dev.gxlg.librgetter.utils.chaining.enchantments;

import dev.gxlg.librgetter.utils.types.exceptions.librgetter.common.InternalErrorException;
import dev.gxlg.versiont.api.V;
import dev.gxlg.versiont.gen.net.minecraft.resources.Identifier;
import dev.gxlg.versiont.gen.net.minecraft.world.item.ItemStack;
import dev.gxlg.versiont.gen.net.minecraft.world.item.enchantment.Enchantment;

public class Enchantments {
    private static Base implementation = null;

    public static Identifier enchantmentId(Enchantment enchantment) {
        return getImpl().enchantmentId(enchantment);
    }

    public static int getEfficiencyLevel(ItemStack stack) {
        return getImpl().getEfficiencyLevel(stack);
    }

    public static boolean canBeTraded(Enchantment enchantment) throws InternalErrorException {
        return getImpl().canBeTraded(enchantment);
    }

    private static Base getImpl() {
        if (implementation != null) {
            return implementation;
        }
        if (V.lower("1.19.3")) {
            implementation = new Enchantments_1_17_0();
        } else if (V.lower("1.21")) {
            implementation = new Enchantments_1_19_3();
        } else {
            implementation = new Enchantments_1_21_0();
        }
        return implementation;
    }

    public abstract static class Base {
        public abstract Identifier enchantmentId(Enchantment enchantment);

        public abstract int getEfficiencyLevel(ItemStack stack);

        public abstract boolean canBeTraded(Enchantment enchantment) throws InternalErrorException;
    }
}
