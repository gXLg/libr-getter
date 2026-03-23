package dev.gxlg.librgetter.utils.chaining.texts;

import dev.gxlg.versiont.gen.net.minecraft.locale.Language;

public class Texts_1_19_4 extends Texts_1_19_0 {
    @Override
    protected String translateIdentifier(Language language, String fullLanguageKey) {
        return language.getOrDefault2(fullLanguageKey);
    }
}
