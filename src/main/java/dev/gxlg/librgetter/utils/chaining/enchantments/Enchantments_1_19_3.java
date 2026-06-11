package dev.gxlg.librgetter.utils.chaining.enchantments;

import dev.gxlg.versiont.gen.net.minecraft.core.registries.BuiltInRegistries;
import dev.gxlg.versiont.gen.net.minecraft.resources.Identifier;
import dev.gxlg.versiont.gen.net.minecraft.world.item.enchantment.Enchantment;

import java.util.List;

public class Enchantments_1_19_3 extends Enchantments_1_17_0 {
    @Override
    public Identifier enchantmentId(Enchantment enchantment) {
        return BuiltInRegistries.ENCHANTMENT().getKey(enchantment);
    }

    @Override
    public List<Enchantment> getAllEnchantments() {
        return enchantmentsFromRegistry(BuiltInRegistries.ENCHANTMENT());
    }
}
