package dev.gxlg.librgetter.utils.chaining.texts;

import dev.gxlg.versiont.gen.net.minecraft.locale.Language;

public class Texts_1_19_4 extends Texts_1_19_0 {
    @Override
    protected String translateEnchantmentId(Language languageWrapper, String fullLanguageKey) {
        return languageWrapper.getOrDefault2(fullLanguageKey);
    }
}
