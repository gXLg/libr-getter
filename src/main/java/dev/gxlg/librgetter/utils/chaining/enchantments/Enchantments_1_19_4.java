package dev.gxlg.librgetter.utils.chaining.enchantments;

import dev.gxlg.multiversion.gen.net.minecraft.locale.LanguageWrapper;

public class Enchantments_1_19_4 extends Enchantments_1_19_3 {
    @Override
    protected String translateEnchantmentId(LanguageWrapper languageWrapper, String fullLanguageKey) {
        return languageWrapper.getOrDefault2(fullLanguageKey);
    }
}
