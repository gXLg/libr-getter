package dev.gxlg.librgetter.utils.reflection;

import dev.gxlg.librgetter.Config;
import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.Reflection;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.config.OptionsConfig;
import dev.gxlg.librgetter.utils.types.config.enums.LogMode;
import dev.gxlg.librgetter.utils.types.config.helpers.Configurable;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.Map;

public class Texts {

    private static final Class<?> tc = (Class<?>) Reflection.wrap(".class_2561/.text.Text");
    private static final Class<?> mc = (Class<?>) Reflection.wrap(".class_5250/.text.MutableText");
    private static final Class<?> fcs;

    static {
        if (Reflection.version(">= 1.19")) {
            fcs = (Class<?>) Reflection.wrap("net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource");
        } else {
            fcs = (Class<?>) Reflection.wrap("net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource");
        }
    }

    private static Object translatable(String message, Object... args) {
        Class<?> arr = Object[].class;
        if (Reflection.version(">= 1.19")) {
            return Reflection.wrap("tc method_43469/translatable String:message arr:args", message, arr, args);
        } else {
            return Reflection.wrap("[.class_2588/.text.TranslatableText] String:message arr:args", message, arr, args);
        }
    }

    private static Object applyStyle(Object text, Style style) {
        Object copy = Reflection.wrap("tc:text method_27662/copy", tc, text);
        Reflection.wrapi("mc:copy method_10862/setStyle style", mc, copy, style);
        return copy;
    }

    private static Object literal(String message) {
        Object m = Text.of(message);
        return Reflection.wrap("tc:m method_27662/copy", tc, m);
    }

    public static void sendError(Object source, String message, Object... args) {
        Object text = translatable(message, args);
        Reflection.wrapi("fcs:source sendError§m tc:text", fcs, source, tc, text);
    }

    public static void sendFound(Object source, EnchantmentTrade enchant, int counter) {
        Object text = translatable("librgetter.found", enchant, counter, enchant.price());
        text = Reflection.wrap("mc:text method_27692/formatted Formatting.GREEN", mc, text);

        if (!LibrGetter.config.removeGoal) {
            String mt = " ";
            text = Reflection.wrap("mc:text method_27693/append mt", mc, text, mt);
            Object rem = translatable("librgetter.remove");
            Style style = Style.EMPTY.withClickEvent(runnable("/librget remove \"" + enchant.id() + "\" " + enchant.lvl())).withColor(Formatting.YELLOW);
            Reflection.wrapi("mc:rem method_10862/setStyle style", mc, rem, style);
            text = Reflection.wrap("mc:text method_10852/append tc:rem", mc, text, tc, rem);
        }
        Reflection.wrapi("fcs:source sendFeedback§m tc:text", fcs, source, tc, text);
    }

    public static void sendFeedback(Object source, String message, Formatting format, Object... args) {
        Object text = translatable(message, args);
        if (format != null) {
            text = Reflection.wrap("mc:text method_27692/formatted format", mc, text, format);
        }
        Reflection.wrapi("fcs:source sendFeedback§m tc:text", fcs, source, tc, text);
    }

    public static void sendMessage(ClientPlayerEntity player, String message, Object... args) {
        if (LibrGetter.config.logMode == LogMode.NONE) return;
        boolean ab = LibrGetter.config.logMode == LogMode.ACTIONBAR;
        Object text = translatable(message, args);
        Reflection.wrapi("@player method_7353/sendMessage tc:text boolean:ab", player, tc, text, ab);
    }

    public static void newVersion(ClientPlayerEntity player, String message, String hover) {
        Object text = translatable(message);
        Text hov = Text.of(hover);
        Style style = Style.EMPTY.withHoverEvent(hoverable(hov));
        Reflection.wrapi("mc:text method_10862/setStyle style", mc, text, style);
        Reflection.wrapi("@player method_7353/sendMessage tc:text boolean:false", player, tc, text);
    }

    public static void list(Object source) {
        Object text = translatable("librgetter.list");
        Object rem = translatable("librgetter.remove");
        for (EnchantmentTrade l : LibrGetter.config.goals) {
            String line = "\n- " + l + " (" + l.price() + ") ";
            text = Reflection.wrap("mc:text method_27693/append line", mc, text, line);
            Style style = Style.EMPTY.withClickEvent(runnable("/librget remove \"" + l.id() + "\" " + l.lvl())).withColor(Formatting.YELLOW);
            Object remc = applyStyle(rem, style);
            text = Reflection.wrap("mc:text method_10852/append tc:remc", mc, text, tc, remc);
        }
        Reflection.wrapi("fcs:source sendFeedback§m tc:text", fcs, source, tc, text);
    }

    public static Object bookMainPage(Map<String, Integer> categories) {
        Object text = Text.of("");
        Object lg = Text.of("LibrGetter " + LibrGetter.getVersion() + "\n");
        lg = Reflection.wrap("tc:lg method_27662/copy", tc, lg);
        lg = Reflection.wrap("mc:lg method_27692/formatted Formatting.DARK_GREEN", mc, lg);
        text = Reflection.wrap("mc:text method_10852/append tc:lg", mc, text, tc, lg);
        Object s = translatable("librgetter.menu");
        text = Reflection.wrap("mc:text method_10852/append tc:s", mc, text, tc, s);
        String nl = "\n";
        String star = "\n* ";
        text = Reflection.wrap("mc:text method_27693/append nl", mc, text, nl);
        for (String cat : Config.CATEGORIES) {
            text = Reflection.wrap("mc:text method_27693/append star", mc, text, star);
            s = applyStyle(translatable("librgetter.category." + cat), Style.EMPTY.withClickEvent(paging(categories.get(cat) + 1)));
            text = Reflection.wrap("mc:text method_10852/append tc:s", mc, text, tc, s);
        }
        return text;
    }

    public static Object bookTitle(String category) {
        Object text = Text.of("");
        Object lg = Text.of("LibrGetter " + LibrGetter.getVersion() + "\n");
        lg = Reflection.wrap("tc:lg method_27662/copy", tc, lg);
        lg = Reflection.wrap("mc:lg method_27692/formatted Formatting.DARK_GREEN", mc, lg);
        text = Reflection.wrap("mc:text method_10852/append tc:lg", mc, text, tc, lg);
        Object s = applyStyle(Text.of("↩"), Style.EMPTY.withClickEvent(paging(1)));
        text = Reflection.wrap("mc:text method_10852/append tc:s", mc, text, tc, s);
        s = literal(" ");
        text = Reflection.wrap("mc:text method_10852/append tc:s", mc, text, tc, s);
        s = translatable("librgetter.category." + category);
        return Reflection.wrap("mc:text method_10852/append tc:s", mc, text, tc, s);
    }

    public static Object bookEntry(Object text, Configurable<?> configurable) {
        String config = configurable.name();
        String showName = config.startsWith("_") ? "+ " + config.substring(1) : config;
        Object name = translatable("librgetter.config." + config);

        Formatting green = configurable.hasEffect() ? Formatting.GREEN : Formatting.GRAY;
        Formatting black = configurable.hasEffect() ? Formatting.BLACK : Formatting.GRAY;
        Formatting red = configurable.hasEffect() ? Formatting.RED : Formatting.GRAY;

        Style cStyle = Style.EMPTY.withHoverEvent(hoverable(name)).withColor(black);
        Object c = applyStyle(Text.of(showName), cStyle);

        String nl = "\n";
        text = Reflection.wrap("mc:text method_27693/append nl+nl", mc, text, nl);
        text = Reflection.wrap("mc:text method_10852/append tc:c", mc, text, tc, c);

        Style r;
        Object x, y, z;
        if (configurable.type() == Boolean.class) {
            boolean value = (boolean) configurable.get();
            Style style = Style.EMPTY.withClickEvent(runnable("/librget config " + config + " " + (!value))).withColor(value ? green : red);

            r = Style.EMPTY.withClickEvent(runnable("/librget config " + config + " " + configurable.getDefault().toString()));
            x = applyStyle(Text.of("[" + value + "]"), style);
            y = literal(" ");
            z = literal("");

        } else if (configurable.type() == Integer.class) {
            int value = (int) configurable.get();
            Style mStyle = configurable.inRange(value - 1) ? Style.EMPTY.withClickEvent(runnable("/librget config " + config + " " + (value - 1))).withColor(red) : Style.EMPTY.withColor(Formatting.GRAY);
            Style nStyle = Style.EMPTY.withColor(black);
            Style pStyle = configurable.inRange(value + 1) ? Style.EMPTY.withClickEvent(runnable("/librget config " + config + " " + (value + 1))).withColor(green) : Style.EMPTY.withColor(Formatting.GRAY);

            r = Style.EMPTY.withClickEvent(runnable("/librget config " + config + " " + configurable.getDefault().toString()));
            x = applyStyle(Text.of("[-]"), mStyle);
            y = applyStyle(Text.of(" " + value + " "), nStyle);
            z = applyStyle(Text.of("[+]"), pStyle);

        } else if (configurable.type() == OptionsConfig.class) {
            OptionsConfig<?> value = (OptionsConfig<?>) configurable.get();
            Style style = Style.EMPTY.withClickEvent(runnable("/librget config " + config + " " + value.next().asString())).withColor(value.asString().equals("NONE") ? red : green);

            r = Style.EMPTY.withClickEvent(runnable("/librget config " + config + " " + ((OptionsConfig<?>) configurable.getDefault()).asString()));
            x = applyStyle(Text.of("[" + value.asString() + "]"), style);
            y = literal("");
            z = literal("");

        } else {
            throw new RuntimeException("Unexpected type of configurable!");
        }

        if (!configurable.isDefault()) {
            Object s = literal(" ");
            text = Reflection.wrap("mc:text method_10852/append tc:s", mc, text, tc, s);
            s = applyStyle(Text.of("↩"), r.withColor(black));
            text = Reflection.wrap("mc:text method_10852/append tc:s", mc, text, tc, s);
        }
        text = Reflection.wrap("mc:text method_27693/append nl", mc, text, nl);


        text = Reflection.wrap("mc:text method_10852/append tc:x", mc, text, tc, x);
        text = Reflection.wrap("mc:text method_10852/append tc:y", mc, text, tc, y);
        return Reflection.wrap("mc:text method_10852/append tc:z", mc, text, tc, z);
    }

    public static ClickEvent runnable(String command) {
        if (Reflection.version(">= 1.21.5")) {
            return (ClickEvent) Reflection.wrap("[.class_2558$class_10609/.text.ClickEvent$RunCommand] String:command", command);
        } else {
            Class<?> action = (Class<?>) Reflection.wrap(".class_2558$class_2559/.text.ClickEvent$Action");
            return (ClickEvent) Reflection.wrap("ClickEvent action:[action field_11750/RUN_COMMAND] String:command", action, command);
        }
    }

    public static ClickEvent paging(int page) {
        if (Reflection.version(">= 1.21.5")) {
            return (ClickEvent) Reflection.wrap("[.class_2558$class_10605/.text.ClickEvent$ChangePage] int:page", page);
        } else {
            Class<?> action = (Class<?>) Reflection.wrap(".class_2558$class_2559/.text.ClickEvent$Action");
            String pageString = String.valueOf(page);
            return (ClickEvent) Reflection.wrap("ClickEvent action:[action field_11748/CHANGE_PAGE] String:pageString", action, pageString);
        }
    }

    public static HoverEvent hoverable(Object text) {
        if (Reflection.version(">= 1.21.5")) {
            return (HoverEvent) Reflection.wrap("[.class_2568$class_10613/.text.HoverEvent$ShowText] tc:text", tc, text);
        } else {
            Class<?> action = (Class<?>) Reflection.wrap(".class_2568$class_5247/.text.HoverEvent$Action");
            return (HoverEvent) Reflection.wrap("HoverEvent action:[action field_24342/SHOW_TEXT] Object:text", action, text);
        }
    }
}
