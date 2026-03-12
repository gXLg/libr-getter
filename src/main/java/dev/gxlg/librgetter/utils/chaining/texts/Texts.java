package dev.gxlg.librgetter.utils.chaining.texts;

import dev.gxlg.librgetter.utils.config.ConfigManager;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.config.helpers.Configurable;
import dev.gxlg.librgetter.utils.types.messages.translatable.TranslatableMessage;
import dev.gxlg.versiont.api.V;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.MutableComponent;

import java.util.List;
import java.util.Map;

public class Texts {
    private static Base implementation = null;

    public static void sendTranslatable(TranslatableMessage translatableMessage) {
        getImpl().sendTranslatable(translatableMessage);
    }

    public static void sendFound(EnchantmentTrade enchant, int counter) {
        getImpl().sendFound(enchant, counter);
    }

    public static void sendTradeLog(List<EnchantmentTrade> offeredEnchantments) {
        getImpl().sendTradeLog(offeredEnchantments);
    }

    public static void sendNewVersion(String message, String hover) {
        getImpl().sendNewVersion(message, hover);
    }

    public static void sendListOfGoals() {
        getImpl().sendListOfGoals();
    }

    public static MutableComponent bookMainPage(Map<ConfigManager.Category, Integer> categories) {
        return getImpl().bookMainPage(categories);
    }

    public static MutableComponent bookTitle(ConfigManager.Category category) {
        return getImpl().bookTitle(category);
    }

    public static MutableComponent bookEntry(MutableComponent text, Configurable<?> configurable) {
        return getImpl().bookEntry(text, configurable);
    }

    public static MutableComponent literal(String text) {
        return getImpl().literal(text);
    }

    public static MutableComponent translatable(String message, Object... args) {
        return getImpl().translatable(message, args);
    }

    public static MutableComponent enchantmentTradeToComponent(EnchantmentTrade trade) {
        return getImpl().enchantmentTradeToComponent(trade);
    }

    private static Base getImpl() {
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

    public abstract static class Base {
        public abstract void sendTranslatable(TranslatableMessage translatableMessage);

        public abstract void sendFound(EnchantmentTrade enchant, int counter);

        public abstract void sendTradeLog(List<EnchantmentTrade> offeredEnchantments);

        public abstract void sendNewVersion(String message, String hover);

        public abstract void sendListOfGoals();

        public abstract MutableComponent bookMainPage(Map<ConfigManager.Category, Integer> categories);

        public abstract MutableComponent bookTitle(ConfigManager.Category category);

        public abstract MutableComponent bookEntry(MutableComponent text, Configurable<?> configurable);

        public abstract MutableComponent literal(String text);

        public abstract MutableComponent translatable(String message, Object... args);

        public abstract MutableComponent enchantmentTradeToComponent(EnchantmentTrade trade);
    }
}
