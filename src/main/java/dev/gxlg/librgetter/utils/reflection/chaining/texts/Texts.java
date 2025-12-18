package dev.gxlg.librgetter.utils.reflection.chaining.texts;

import dev.gxlg.librgetter.multiversion.V;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.config.helpers.Configurable;
import net.minecraft.util.Formatting;

import java.util.List;
import java.util.Map;

public abstract class Texts {
    abstract public void sendMessage(Object text, boolean actionbar);

    abstract public void sendTranslatable(Formatting format, String message, Object... args);

    abstract public void sendTranslatableFeedback(String message, Object... args);

    abstract public void sendTranslatableSuccess(String message, Object... args);

    abstract public void sendTranslatableError(String message, Object... args);

    abstract public void sendTranslatableWarning(String message, Object... args);

    abstract public void sendFound(EnchantmentTrade enchant, int counter);

    abstract public void sendTradeLog(List<EnchantmentTrade> offeredEnchantments);

    abstract public void newVersion(String message, String hover);

    abstract public void listGoals();

    abstract public Object bookMainPage(Map<String, Integer> categories);

    abstract public Object bookTitle(String category);

    abstract public Object bookEntry(Object text, Configurable<?> configurable);

    private static Texts implementation = null;
    public static Texts getImpl() {
        if (implementation != null) return implementation;
        if (V.lower("1.19")) {
            implementation = new Texts_1_17_0();
        } else if (V.lower("1.21.5")) {
            implementation = new Texts_1_19_0();
        } else {
            implementation = new Texts_1_21_5();
        }
        return implementation;
    }
}
