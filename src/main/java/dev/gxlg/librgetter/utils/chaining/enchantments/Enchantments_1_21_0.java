package dev.gxlg.librgetter.utils.chaining.enchantments;

import dev.gxlg.librgetter.utils.types.exceptions.librgetter.common.InternalErrorException;
import dev.gxlg.multiversion.gen.net.minecraft.client.MinecraftWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.ClientLevelWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.core.RegistryWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.core.registries.RegistriesWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.resources.IdentifierWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.resources.ResourceKeyWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.tags.EnchantmentTagsWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemStackWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.item.enchantment.EnchantmentWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.item.enchantment.EnchantmentsWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.item.enchantment.ItemEnchantmentsWrapper;
import it.unimi.dsi.fastutil.objects.Object2IntMap;

public class Enchantments_1_21_0 extends Enchantments_1_19_3 {
    @Override
    public IdentifierWrapper enchantmentId(EnchantmentWrapper enchantment) {
        ClientLevelWrapper world = MinecraftWrapper.getInstance().getLevelField();
        if (world == null) {
            return null;
        }
        return world.registryAccess().lookupOrThrow(RegistriesWrapper.ENCHANTMENT()).getKey(enchantment.unwrap());
    }

    @Override
    public int getEfficiencyLevel(ItemStackWrapper stack) {
        ResourceKeyWrapper efficiency = EnchantmentsWrapper.EFFICIENCY2();
        ItemEnchantmentsWrapper enchantments = stack.getEnchantments();
        return enchantments.entrySet().stream().filter(e -> e.getKey().is(efficiency)).findFirst().map(Object2IntMap.Entry::getIntValue).orElse(0);
    }

    @Override
    public boolean canBeTraded(EnchantmentWrapper enchantment) throws InternalErrorException {
        ClientLevelWrapper world = MinecraftWrapper.getInstance().getLevelField();
        if (world == null) {
            throw new InternalErrorException("world");
        }
        RegistryWrapper registry = world.registryAccess().lookupOrThrow(RegistriesWrapper.ENCHANTMENT());
        return registry.wrapAsHolder(enchantment.unwrap()).is(EnchantmentTagsWrapper.TRADEABLE());
    }
}
