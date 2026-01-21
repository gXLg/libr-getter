package dev.gxlg.librgetter.utils.chaining.parser;

import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.multiversion.gen.net.minecraft.core.HolderWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.core.component.DataComponentHolderWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.core.component.DataComponentsWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.nbt.CompoundTagWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.item.component.CustomDataWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.item.enchantment.ItemEnchantmentsWrapper;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.world.item.ItemStack;

import java.util.Set;

public class Parser_1_20_5 extends Parser_1_17_0 {
    @Override
    protected CompoundTagWrapper getCustomData(ItemStack stack) {
        CustomDataWrapper customData = CustomDataWrapper.inst(DataComponentHolderWrapper.inst(stack).get(DataComponentsWrapper.CUSTOM_DATA()));
        if (customData.isNull()) {
            return null;
        }
        return customData.copyTag();
    }

    @Override
    protected EnchantmentTrade.EnchantmentOnly parseStored(ItemStack stack) {
        Set<Object2IntMap.Entry<HolderWrapper>> enchantmentSet = Set.of();

        // Legacy enchantment books
        ItemEnchantmentsWrapper enchantments = ItemEnchantmentsWrapper.inst(DataComponentHolderWrapper.inst(stack).get(DataComponentsWrapper.ENCHANTMENTS()));
        if (!enchantments.isNull()) {
            enchantmentSet = enchantments.entrySet();
            if (enchantmentSet.isEmpty()) {
                enchantmentSet = null;
            }
        }

        // Vanilla
        if (enchantmentSet == null) {
            enchantments = ItemEnchantmentsWrapper.inst(DataComponentHolderWrapper.inst(stack).get(DataComponentsWrapper.STORED_ENCHANTMENTS()));
            if (!enchantments.isNull()) {
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

        for (Object2IntMap.Entry<HolderWrapper> e : enchantmentSet) {
            return new EnchantmentTrade.EnchantmentOnly(e.getKey().getRegisteredName(), e.getIntValue());
        }
        return null;
    }
}
