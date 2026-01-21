package dev.gxlg.librgetter.utils.chaining.enchantments;

import dev.gxlg.multiversion.gen.net.minecraft.core.registries.BuiltInRegistriesWrapper;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.enchantment.Enchantment;

public class Enchantments_1_19_3 extends Enchantments_1_17_0 {
    @Override
    public Identifier enchantmentId(Enchantment enchantment) {
        return BuiltInRegistriesWrapper.ENCHANTMENT().getKey(enchantment);
    }
}
