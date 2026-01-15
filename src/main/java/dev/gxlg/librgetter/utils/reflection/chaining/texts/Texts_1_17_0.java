package dev.gxlg.librgetter.utils.reflection.chaining.texts;

import dev.gxlg.librgetter.Config;
import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.config.OptionsConfig;
import dev.gxlg.librgetter.utils.types.config.enums.LogMode;
import dev.gxlg.librgetter.utils.types.config.helpers.Configurable;
import dev.gxlg.multiversion.gen.net.minecraft.client.player.LocalPlayerWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.network.chat.ClickEvent$ActionWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.network.chat.ClickEventWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.network.chat.ComponentWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.network.chat.HoverEvent$ActionWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.network.chat.HoverEventWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.network.chat.MutableComponentWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.network.chat.contents.TranslatableContentsWrapper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;

import java.util.List;
import java.util.Map;

public class Texts_1_17_0 extends Texts {
    @Override
    public void sendMessage(ComponentWrapper text, boolean actionbar) {
        Minecraft client = Minecraft.getInstance();
        LocalPlayer player = client.player;
        if (player == null) {
            return;
        }
        LocalPlayerWrapper.inst(player).displayClientMessage(text, actionbar);
    }

    public void sendTranslatable(ChatFormatting format, String message, Object... args) {
        MutableComponentWrapper text = translatable(message, args);
        if (format != null) {
            text = text.withStyle(format);
        }
        sendMessage(text, false);
    }

    @Override
    public void sendTranslatableError(String message, Object... args) {
        sendTranslatable(ChatFormatting.RED, message, args);
    }

    @Override
    public void sendTranslatableWarning(String message, Object... args) {
        sendTranslatable(ChatFormatting.YELLOW, message, args);
    }

    @Override
    public void sendTranslatableFeedback(String message, Object... args) {
        sendTranslatable(null, message, args);
    }

    @Override
    public void sendTranslatableSuccess(String message, Object... args) {
        sendTranslatable(ChatFormatting.GREEN, message, args);
    }

    @Override
    public void sendFound(EnchantmentTrade enchant, int counter) {
        MutableComponentWrapper text = translatable("librgetter.found", enchant, counter, enchant.price());
        text = text.withStyle(ChatFormatting.GREEN);

        if (!LibrGetter.config.removeGoal) {
            text = text.append(" ");
            MutableComponentWrapper rem = translatable("librgetter.remove");
            Style style = Style.EMPTY.withClickEvent(runnable("/librget remove \"" + enchant.id() + "\" " + enchant.lvl())).withColor(ChatFormatting.YELLOW);
            rem.setStyle(style);
            text = text.append(rem);
        }
        sendMessage(text, false);
    }

    public void sendTradeLog(List<EnchantmentTrade> offeredEnchantments) {
        if (LibrGetter.config.logMode == LogMode.NONE) {
            return;
        }
        boolean ab = LibrGetter.config.logMode == LogMode.ACTIONBAR;
        MutableComponentWrapper text = translatable("librgetter.offer", offeredEnchantments);
        sendMessage(text, ab);
    }

    @Override
    public void sendNewVersion(String message, String hover) {
        MutableComponentWrapper text = translatable(message);
        MutableComponentWrapper hov = literal(hover);
        Style style = Style.EMPTY.withHoverEvent(hoverable(hov));
        text.setStyle(style);
        sendMessage(text, false);
    }

    @Override
    public void sendListOfGoals() {
        MutableComponentWrapper text = translatable("librgetter.list");
        MutableComponentWrapper rem = translatable("librgetter.remove");
        for (EnchantmentTrade l : LibrGetter.config.goals) {
            String line = "\n- " + l + " (" + l.price() + ") ";
            text = text.append(line);
            Style style = Style.EMPTY.withClickEvent(runnable("/librget remove \"" + l.id() + "\" " + l.lvl())).withColor(ChatFormatting.YELLOW);
            MutableComponentWrapper remc = applyStyle(rem, style);
            text = text.append(remc);
        }
        sendMessage(text, false);
    }

    @Override
    public MutableComponentWrapper bookMainPage(Map<String, Integer> categories) {
        MutableComponentWrapper text = title();
        MutableComponentWrapper s = translatable("librgetter.menu");
        text = text.append(s);
        text = text.append("\n");
        for (String cat : Config.CATEGORIES) {
            text = text.append("\n* ");
            s = applyStyle(translatable("librgetter.category." + cat), Style.EMPTY.withClickEvent(paging(categories.get(cat) + 1)));
            text = text.append(s);
        }
        return text;
    }

    @Override
    public MutableComponentWrapper bookTitle(String category) {
        MutableComponentWrapper text = title();
        MutableComponentWrapper s = applyStyle(literal("↩"), Style.EMPTY.withClickEvent(paging(1)));
        text = text.append(s);
        s = literal(" ");
        text = text.append(s);
        s = translatable("librgetter.category." + category);
        return text.append(s);
    }

    @Override
    public MutableComponentWrapper bookEntry(MutableComponentWrapper text, Configurable<?> configurable) {
        String config = configurable.name();
        String showName = config.startsWith("_") ? "+ " + config.substring(1) : config;
        MutableComponentWrapper name = translatable("librgetter.config." + config);

        ChatFormatting green = configurable.hasEffect() ? ChatFormatting.GREEN : ChatFormatting.GRAY;
        ChatFormatting black = configurable.hasEffect() ? ChatFormatting.BLACK : ChatFormatting.GRAY;
        ChatFormatting red = configurable.hasEffect() ? ChatFormatting.RED : ChatFormatting.GRAY;

        Style cStyle = Style.EMPTY.withHoverEvent(hoverable(name)).withColor(black);
        MutableComponentWrapper c = applyStyle(literal(showName), cStyle);

        text = text.append("\n\n").append(c);

        Style r;
        MutableComponentWrapper x, y, z;
        if (configurable.type() == Boolean.class) {
            boolean value = (boolean) configurable.get();
            Style style = Style.EMPTY.withClickEvent(runnable("/librget config " + config + " " + (!value))).withColor(value ? green : red);

            r = Style.EMPTY.withClickEvent(runnable("/librget config " + config + " " + configurable.getDefault().toString()));
            x = applyStyle(literal("[" + value + "]"), style);
            y = literal(" ");
            z = literal("");

        } else if (configurable.type() == Integer.class) {
            int value = (int) configurable.get();
            Style mStyle =
                configurable.inRange(value - 1) ? Style.EMPTY.withClickEvent(runnable("/librget config " + config + " " + (value - 1))).withColor(red) : Style.EMPTY.withColor(ChatFormatting.GRAY);
            Style nStyle = Style.EMPTY.withColor(black);
            Style pStyle =
                configurable.inRange(value + 1) ? Style.EMPTY.withClickEvent(runnable("/librget config " + config + " " + (value + 1))).withColor(green) : Style.EMPTY.withColor(ChatFormatting.GRAY);

            r = Style.EMPTY.withClickEvent(runnable("/librget config " + config + " " + configurable.getDefault().toString()));
            x = applyStyle(literal("[-]"), mStyle);
            y = applyStyle(literal(" " + value + " "), nStyle);
            z = applyStyle(literal("[+]"), pStyle);

        } else if (configurable.type() == OptionsConfig.class) {
            OptionsConfig<?> value = (OptionsConfig<?>) configurable.get();
            Style style = Style.EMPTY.withClickEvent(runnable("/librget config " + config + " " + value.next().getSerializedName())).withColor(value.getSerializedName().equals("NONE") ? red : green);

            r = Style.EMPTY.withClickEvent(runnable("/librget config " + config + " " + ((OptionsConfig<?>) configurable.getDefault()).getSerializedName()));
            x = applyStyle(literal("[" + value.getSerializedName() + "]"), style);
            y = literal("");
            z = literal("");

        } else {
            // TODO: centralized exceptions
            throw new RuntimeException("Unexpected type of configurable!");
        }

        if (!configurable.isDefault()) {
            MutableComponentWrapper s = literal(" ");
            text = text.append(s);
            s = applyStyle(literal("↩"), r.withColor(black));
            text = text.append(s);
        }
        return text.append("\n").append(x).append(y).append(z);
    }

    protected ClickEvent runnable(String command) {
        return new ClickEventWrapper(ClickEvent$ActionWrapper.RUN_COMMAND(), command).unwrap(ClickEvent.class);
    }

    protected ClickEvent paging(int page) {
        String pageString = String.valueOf(page);
        return new ClickEventWrapper(ClickEvent$ActionWrapper.CHANGE_PAGE(), pageString).unwrap(ClickEvent.class);
    }

    protected HoverEvent hoverable(ComponentWrapper text) {
        return new HoverEventWrapper(HoverEvent$ActionWrapper.SHOW_TEXT(), text).unwrap(HoverEvent.class);
    }

    protected MutableComponentWrapper translatable(String message, Object... args) {
        return new TranslatableContentsWrapper(message, args);
    }

    protected MutableComponentWrapper applyStyle(ComponentWrapper text, Style style) {
        MutableComponentWrapper copy = text.plainCopy();
        copy.setStyle(style);
        return copy;
    }

    protected MutableComponentWrapper literal(String message) {
        Component m = Component.nullToEmpty(message);
        return ComponentWrapper.inst(m).plainCopy();
    }

    protected MutableComponentWrapper title() {
        MutableComponentWrapper text = literal("");
        MutableComponentWrapper lg = literal("LibrGetter " + LibrGetter.getVersion() + "\n");
        lg = lg.withStyle(ChatFormatting.DARK_GREEN);
        return text.append(lg);
    }
}
