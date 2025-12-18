package dev.gxlg.librgetter.utils.reflection.chaining.texts;

import dev.gxlg.librgetter.multiversion.V;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.config.helpers.Configurable;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.Formatting;

import java.util.Map;

public abstract class Texts {
    abstract public void sendError(Object source, String message, Object... args);

    abstract public void sendFound(Object source, EnchantmentTrade enchant, int counter);

    abstract public void sendFeedback(Object source, String message, Formatting format, Object... args);

    abstract public void sendTradeLog(ClientPlayerEntity player, String message, Object... args);

    abstract public void newVersion(ClientPlayerEntity player, String message, String hover);

    abstract public void list(Object source);

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
