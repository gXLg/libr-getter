package dev.gxlg.librgetter.utils.chaining.enchantments;

import dev.gxlg.librgetter.utils.types.exceptions.librgetter.common.InternalErrorException;
import dev.gxlg.multiversion.V;
import dev.gxlg.multiversion.gen.net.minecraft.resources.IdentifierWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemStackWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.item.enchantment.EnchantmentWrapper;

public abstract class Enchantments {
    public abstract IdentifierWrapper enchantmentId(EnchantmentWrapper enchantment);

    public abstract int getEfficiencyLevel(ItemStackWrapper stack);

    public abstract boolean canBeTraded(EnchantmentWrapper enchantment) throws InternalErrorException;

    private static Enchantments implementation = null;

    public static Enchantments getImpl() {
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
}
