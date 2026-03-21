package dev.gxlg.librgetter.utils.chaining.enchantments;

import dev.gxlg.librgetter.utils.types.exceptions.librgetter.common.InternalErrorException;
import dev.gxlg.versiont.gen.net.minecraft.core.Registry;
import dev.gxlg.versiont.gen.net.minecraft.resources.Identifier;
import dev.gxlg.versiont.gen.net.minecraft.world.item.ItemStack;
import dev.gxlg.versiont.gen.net.minecraft.world.item.enchantment.Enchantment;
import dev.gxlg.versiont.gen.net.minecraft.world.item.enchantment.EnchantmentHelper;
import dev.gxlg.versiont.gen.net.minecraft.world.item.enchantment.Enchantments;

public class Enchantments_1_17_0 extends dev.gxlg.librgetter.utils.chaining.enchantments.Enchantments.Base {
    @Override
    public Identifier enchantmentId(Enchantment enchantment) {
        return Registry.ENCHANTMENT().getKey(enchantment);
    }

    @Override
    public int getEfficiencyLevel(ItemStack stack) {
        return EnchantmentHelper.getItemEnchantmentLevel(Enchantments.EFFICIENCY(), stack);
    }

    @Override
    public boolean canBeTraded(Enchantment enchantment) throws InternalErrorException {
        return enchantment.isTradeable();
    }
}
