package dev.gxlg.librgetter.utils.chaining.enchantments;

import dev.gxlg.multiversion.gen.net.minecraft.core.registries.BuiltInRegistriesWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.resources.IdentifierWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.item.enchantment.EnchantmentWrapper;

public class Enchantments_1_19_3 extends Enchantments_1_17_0 {
    @Override
    public IdentifierWrapper enchantmentId(EnchantmentWrapper enchantment) {
        return BuiltInRegistriesWrapper.ENCHANTMENT().getKey(enchantment.unwrap());
    }
}
