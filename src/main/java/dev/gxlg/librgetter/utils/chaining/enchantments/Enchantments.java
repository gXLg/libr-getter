package dev.gxlg.librgetter.utils.chaining.enchantments;

import dev.gxlg.librgetter.utils.types.exceptions.librgetter.common.InternalErrorException;
import dev.gxlg.multiversion.V;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public abstract class Enchantments {
    public abstract Identifier enchantmentId(Enchantment enchantment);

    public abstract int getEfficiencyLevel(ItemStack stack);

    public abstract boolean canBeTraded(Enchantment enchantment) throws InternalErrorException;

    public abstract String translateEnchantmentId(String stringId);

    private static Enchantments implementation = null;

    public static Enchantments getImpl() {
        if (implementation != null) {
            return implementation;
        }
        if (V.lower("1.19.3")) {
            implementation = new Enchantments_1_17_0();
        } else if (V.lower("1.19.4")) {
            implementation = new Enchantments_1_19_3();
        } else if (V.lower("1.21")) {
            implementation = new Enchantments_1_19_4();
        } else {
            implementation = new Enchantments_1_21_0();
        }
        return implementation;
    }
}
