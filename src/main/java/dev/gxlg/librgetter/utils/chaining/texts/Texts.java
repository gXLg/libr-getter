package dev.gxlg.librgetter.utils.chaining.texts;

import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.config.helpers.Configurable;
import dev.gxlg.librgetter.utils.types.messages.translatable.TranslatableMessage;
import dev.gxlg.versiont.api.V;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.MutableComponent;

import java.util.List;
import java.util.Map;

public abstract class Texts {
    abstract public void sendTranslatable(TranslatableMessage translatableMessage);

    abstract public void sendFound(EnchantmentTrade enchant, int counter);

    abstract public void sendTradeLog(List<EnchantmentTrade> offeredEnchantments);

    abstract public void sendNewVersion(String message, String hover);

    abstract public void sendListOfGoals();

    abstract public MutableComponent bookMainPage(Map<String, Integer> categories);

    abstract public MutableComponent bookTitle(String category);

    abstract public MutableComponent bookEntry(MutableComponent text, Configurable<?> configurable);

    abstract public MutableComponent literal(String text);

    abstract public MutableComponent translatable(String message, Object... args);

    abstract public MutableComponent enchantmentTradeToComponent(EnchantmentTrade trade);

    private static Texts implementation = null;

    public static Texts getImpl() {
        if (implementation != null) {
            return implementation;
        }
        if (V.lower("1.19")) {
            implementation = new Texts_1_17_0();
        } else if (V.lower("1.19.4")) {
            implementation = new Texts_1_19_0();
        } else if (V.lower("1.21.5")) {
            implementation = new Texts_1_19_4();
        } else {
            implementation = new Texts_1_21_5();
        }
        return implementation;
    }
}
