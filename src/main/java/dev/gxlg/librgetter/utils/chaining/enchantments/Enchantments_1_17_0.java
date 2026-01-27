package dev.gxlg.librgetter.utils.chaining.enchantments;

import dev.gxlg.librgetter.utils.types.exceptions.librgetter.common.InternalErrorException;
import dev.gxlg.multiversion.gen.net.minecraft.core.RegistryWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.resources.IdentifierWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemStackWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.item.enchantment.EnchantmentHelperWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.item.enchantment.EnchantmentWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.item.enchantment.EnchantmentsWrapper;

public class Enchantments_1_17_0 extends Enchantments {
    @Override
    public IdentifierWrapper enchantmentId(EnchantmentWrapper enchantment) {
        return RegistryWrapper.ENCHANTMENT().getKey(enchantment.unwrap());
    }

    @Override
    public int getEfficiencyLevel(ItemStackWrapper stack) {
        return EnchantmentHelperWrapper.getItemEnchantmentLevel(EnchantmentsWrapper.EFFICIENCY(), stack);
    }

    @Override
    public boolean canBeTraded(EnchantmentWrapper enchantment) throws InternalErrorException {
        return enchantment.isTradeable();
    }
}
