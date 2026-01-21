package dev.gxlg.librgetter.utils.chaining.enchantments;

import dev.gxlg.librgetter.utils.types.exceptions.librgetter.common.InternalErrorException;
import dev.gxlg.multiversion.gen.net.minecraft.core.RegistryWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.locale.LanguageWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.item.enchantment.EnchantmentHelperWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.item.enchantment.EnchantmentWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.item.enchantment.EnchantmentsWrapper;
import net.minecraft.locale.Language;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public class Enchantments_1_17_0 extends Enchantments {
    @Override
    public Identifier enchantmentId(Enchantment enchantment) {
        return RegistryWrapper.ENCHANTMENT().getKey(enchantment);
    }

    @Override
    public int getEfficiencyLevel(ItemStack stack) {
        return EnchantmentHelperWrapper.getItemEnchantmentLevel(EnchantmentsWrapper.EFFICIENCY(), stack);
    }

    @Override
    public boolean canBeTraded(Enchantment enchantment) throws InternalErrorException {
        return EnchantmentWrapper.inst(enchantment).isTradeable();
    }

    @Override
    public String translateEnchantmentId(String stringId) {
        Identifier id = Identifier.tryParse(stringId);
        if (id == null) {
            return stringId;
        }
        return translateEnchantmentId(id);
    }

    private String translateEnchantmentId(Identifier id) {
        Language lang = Language.getInstance();
        String fullLanguageKey = "enchantment." + id.getNamespace() + "." + id.getPath();
        return translateEnchantmentId(LanguageWrapper.inst(lang), fullLanguageKey);
    }

    protected String translateEnchantmentId(LanguageWrapper languageWrapper, String fullLanguageKey) {
        return languageWrapper.getOrDefault(fullLanguageKey);
    }
}
