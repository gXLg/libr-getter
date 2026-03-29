package dev.gxlg.librgetter.utils.chaining.enchantments;

import dev.gxlg.librgetter.utils.types.exceptions.common.InternalErrorException;
import dev.gxlg.versiont.gen.net.minecraft.client.Minecraft;
import dev.gxlg.versiont.gen.net.minecraft.client.multiplayer.ClientLevel;
import dev.gxlg.versiont.gen.net.minecraft.core.Registry;
import dev.gxlg.versiont.gen.net.minecraft.core.registries.Registries;
import dev.gxlg.versiont.gen.net.minecraft.resources.Identifier;
import dev.gxlg.versiont.gen.net.minecraft.resources.ResourceKey;
import dev.gxlg.versiont.gen.net.minecraft.tags.EnchantmentTags;
import dev.gxlg.versiont.gen.net.minecraft.world.item.ItemStack;
import dev.gxlg.versiont.gen.net.minecraft.world.item.enchantment.Enchantment;
import dev.gxlg.versiont.gen.net.minecraft.world.item.enchantment.Enchantments;
import dev.gxlg.versiont.gen.net.minecraft.world.item.enchantment.ItemEnchantments;
import it.unimi.dsi.fastutil.objects.Object2IntMap;

public class Enchantments_1_21_0 extends Enchantments_1_19_3 {
    @Override
    public Identifier enchantmentId(Enchantment enchantment) {
        ClientLevel world = Minecraft.getInstance().getLevelField();
        if (world == null) {
            return null;
        }
        return world.registryAccess().lookupOrThrow(Registries.ENCHANTMENT()).getKey(enchantment);
    }

    @Override
    public int getEfficiencyLevel(ItemStack stack) {
        ResourceKey efficiency = Enchantments.EFFICIENCY2();
        ItemEnchantments enchantments = stack.getEnchantments();
        return enchantments.entrySet().stream().filter(e -> e.getKey().is(efficiency)).findFirst().map(Object2IntMap.Entry::getIntValue).orElse(0);
    }

    @Override
    public boolean canBeTraded(Enchantment enchantment) throws InternalErrorException {
        ClientLevel world = Minecraft.getInstance().getLevelField();
        if (world == null) {
            throw new InternalErrorException("world");
        }
        Registry registry = world.registryAccess().lookupOrThrow(Registries.ENCHANTMENT());
        return registry.wrapAsHolder(enchantment).is(EnchantmentTags.TRADEABLE());
    }
}
