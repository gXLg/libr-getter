package dev.gxlg.librgetter.utils.reflection.chaining.texts;

import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.config.helpers.Configurable;
import dev.gxlg.librgetter.utils.types.translatable_messages.TranslatableMessage;
import dev.gxlg.multiversion.V;
import dev.gxlg.multiversion.gen.net.minecraft.network.chat.ComponentWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.network.chat.MutableComponentWrapper;
import net.minecraft.ChatFormatting;

import java.util.List;
import java.util.Map;

public abstract class Texts {
    abstract public void sendMessage(ComponentWrapper text, boolean actionbar);

    abstract public void sendTranslatable(ChatFormatting format, String translationKey, Object... args);

    abstract public void sendTranslatable(TranslatableMessage translatableMessage);

    abstract public void sendFound(EnchantmentTrade enchant, int counter);

    abstract public void sendTradeLog(List<EnchantmentTrade> offeredEnchantments);

    abstract public void sendNewVersion(String message, String hover);

    abstract public void sendListOfGoals();

    abstract public MutableComponentWrapper bookMainPage(Map<String, Integer> categories);

    abstract public MutableComponentWrapper bookTitle(String category);

    abstract public MutableComponentWrapper bookEntry(MutableComponentWrapper text, Configurable<?> configurable);

    private static Texts implementation = null;

    public static Texts getImpl() {
        if (implementation != null) {
            return implementation;
        }
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
