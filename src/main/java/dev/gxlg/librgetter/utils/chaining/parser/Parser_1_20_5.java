package dev.gxlg.librgetter.utils.chaining.parser;

import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.versiont.gen.net.minecraft.core.Holder;
import dev.gxlg.versiont.gen.net.minecraft.core.component.DataComponents;
import dev.gxlg.versiont.gen.net.minecraft.nbt.CompoundTag;
import dev.gxlg.versiont.gen.net.minecraft.world.item.ItemStack;
import dev.gxlg.versiont.gen.net.minecraft.world.item.component.CustomData;
import dev.gxlg.versiont.gen.net.minecraft.world.item.enchantment.ItemEnchantments;
import it.unimi.dsi.fastutil.objects.Object2IntMap;

import java.util.Set;

public class Parser_1_20_5 extends Parser_1_17_0 {
    @Override
    protected CompoundTag getCustomData(ItemStack stack) {
        CustomData customData = (CustomData) stack.get(DataComponents.CUSTOM_DATA());
        if (customData == null) {
            return null;
        }
        return customData.copyTag();
    }

    @Override
    protected EnchantmentTrade.EnchantmentOnly parseStored(ItemStack stack) {
        Set<Object2IntMap.Entry<Holder>> enchantmentSet = Set.of();

        // Legacy enchantment books
        ItemEnchantments enchantments = (ItemEnchantments) stack.get(DataComponents.ENCHANTMENTS());
        if (enchantments != null) {
            enchantmentSet = enchantments.entrySet();
            if (enchantmentSet.isEmpty()) {
                enchantmentSet = null;
            }
        }

        // Vanilla
        if (enchantmentSet == null) {
            enchantments = (ItemEnchantments) stack.get(DataComponents.STORED_ENCHANTMENTS());
            if (enchantments != null) {
                enchantmentSet = enchantments.entrySet();
                if (enchantmentSet.isEmpty()) {
                    enchantmentSet = null;
                }
            }
        }

        // Insert more methods to find an enchantment here
        // ...

        if (enchantmentSet == null) {
            return null;
        }

        for (Object2IntMap.Entry<Holder> e : enchantmentSet) {
            return new EnchantmentTrade.EnchantmentOnly(e.getKey().getRegisteredName(), e.getIntValue());
        }
        return null;
    }
}
