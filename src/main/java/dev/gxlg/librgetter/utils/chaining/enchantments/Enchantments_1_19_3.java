package dev.gxlg.librgetter.utils.chaining.enchantments;

import dev.gxlg.versiont.gen.net.minecraft.core.registries.BuiltInRegistries;
import dev.gxlg.versiont.gen.net.minecraft.resources.Identifier;
import dev.gxlg.versiont.gen.net.minecraft.world.item.enchantment.Enchantment;

public class Enchantments_1_19_3 extends Enchantments_1_17_0 {
    @Override
    public Identifier enchantmentId(Enchantment enchantment) {
        return BuiltInRegistries.ENCHANTMENT().getKey(enchantment);
    }
}
