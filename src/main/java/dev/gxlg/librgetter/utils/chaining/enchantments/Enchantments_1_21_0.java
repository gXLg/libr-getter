package dev.gxlg.librgetter.utils.chaining.enchantments;

import dev.gxlg.librgetter.utils.types.exceptions.librgetter.common.InternalErrorException;
import dev.gxlg.multiversion.gen.net.minecraft.core.RegistryAccessWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.core.RegistryWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.core.registries.RegistriesWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.resources.ResourceKeyWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.tags.EnchantmentTagsWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemStackWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.item.enchantment.EnchantmentsWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.item.enchantment.ItemEnchantmentsWrapper;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public class Enchantments_1_21_0 extends Enchantments_1_19_4 {
    @Override
    public Identifier enchantmentId(Enchantment enchantment) {
        ClientLevel world = Minecraft.getInstance().level;
        if (world == null) {
            return null;
        }
        return RegistryAccessWrapper.inst(world.registryAccess()).lookupOrThrow(RegistriesWrapper.ENCHANTMENT()).getKey(enchantment);
    }

    @Override
    public int getEfficiencyLevel(ItemStack stack) {
        ResourceKeyWrapper efficiency = EnchantmentsWrapper.EFFICIENCY2();
        ItemEnchantmentsWrapper enchantments = ItemStackWrapper.inst(stack).getEnchantments();
        return enchantments.entrySet().stream().filter(e -> e.getKey().is(efficiency)).findFirst().map(Object2IntMap.Entry::getIntValue).orElse(0);

    }

    @Override
    public boolean canBeTraded(Enchantment enchantment) throws InternalErrorException {
        ClientLevel world = Minecraft.getInstance().level;
        if (world == null) {
            throw new InternalErrorException("world");
        }
        RegistryWrapper registry = RegistryAccessWrapper.inst(world.registryAccess()).lookupOrThrow(RegistriesWrapper.ENCHANTMENT());
        return registry.wrapAsHolder(enchantment).is(EnchantmentTagsWrapper.TRADEABLE());

    }
}
