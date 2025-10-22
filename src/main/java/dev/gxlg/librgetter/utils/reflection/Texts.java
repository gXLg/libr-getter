package dev.gxlg.librgetter.utils.reflection;

import dev.gxlg.librgetter.Config;
import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.Reflection;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

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

    public static void sendError(Object source, String message, Object... args) {
        Object text = translatable(message, args);
        Reflection.wrapi("fcs:source sendError§m tc:text", fcs, source, tc, text);
    }

    public static void sendFound(Object source, Config.Enchantment enchant, int counter) {
        Object text = translatable("librgetter.found", enchant, counter, enchant.price);
        text = Reflection.wrap("mc:text method_27692/formatted Formatting.GREEN", mc, text);

        if (!LibrGetter.config.removeGoal) {
            String mt = " ";
            text = Reflection.wrap("mc:text method_27693/append mt", mc, text, mt);
            Object rem = translatable("librgetter.remove");
            Style style = Style.EMPTY.withClickEvent(runnable("/librget remove \"" + enchant.id + "\" " + enchant.lvl)).withColor(Formatting.YELLOW);
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

    public static void sendMessage(ClientPlayerEntity player, String message, boolean ab, Object... args) {
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
        for (Config.Enchantment l : LibrGetter.config.goals) {
            String line = "\n- " + l + " (" + l.price + ") ";
            text = Reflection.wrap("mc:text method_27693/append line", mc, text, line);
            Style style = Style.EMPTY.withClickEvent(runnable("/librget remove \"" + l.id + "\" " + l.lvl)).withColor(Formatting.YELLOW);
            Object remc = applyStyle(rem, style);
            text = Reflection.wrap("mc:text method_10852/append tc:remc", mc, text, tc, remc);
        }
        Reflection.wrapi("fcs:source sendFeedback§m tc:text", fcs, source, tc, text);
    }

    public static Object bookTitle() {
        Object text = Text.of("");
        Object lg = Text.of("LibrGetter " + LibrGetter.getVersion() + "\n");
        lg = Reflection.wrap("tc:lg method_27662/copy", tc, lg);
        lg = Reflection.wrap("mc:lg method_27692/formatted Formatting.DARK_GREEN", mc, lg);
        text = Reflection.wrap("mc:text method_10852/append tc:lg", mc, text, tc, lg);
        Object menu = translatable("librgetter.menu");
        return Reflection.wrap("mc:text method_10852/append tc:menu", mc, text, tc, menu);
    }

    public static Object bookEntry(Object text, Config.Configurable<?> configurable) {
        String config = configurable.name();
        String showName = config.startsWith("_") ? "+ " + config.substring(1) : config;
        Object name = translatable("librgetter.config." + config);

        Style cStyle = Style.EMPTY.withHoverEvent(hoverable(name));
        Object c = applyStyle(Text.of(showName), cStyle);

        String nl = "\n";
        text = Reflection.wrap("mc:text method_27693/append nl+nl", mc, text, nl);
        text = Reflection.wrap("mc:text method_10852/append tc:c", mc, text, tc, c);
        text = Reflection.wrap("mc:text method_27693/append nl", mc, text, nl);

        Object t, f;
        String m;

        if (configurable.type() == Boolean.class) {
            boolean value = (boolean) configurable.get();
            Style tStyle = Style.EMPTY.withClickEvent(runnable("/librget config " + config + " true")).withColor(Formatting.GREEN).withUnderline(value);
            Style fStyle = Style.EMPTY.withClickEvent(runnable("/librget config " + config + " false")).withColor(Formatting.RED).withUnderline(!value);

            t = applyStyle(Text.of("[true]"), tStyle);
            f = applyStyle(Text.of("[false]"), fStyle);
            m = " ";

        } else if (configurable.type() == Integer.class) {
            int value = (int) configurable.get();
            Style mStyle = configurable.inRange(value - 1) ? Style.EMPTY.withClickEvent(runnable("/librget config " + config + " " + (value - 1))).withColor(Formatting.RED) : Style.EMPTY.withColor(Formatting.GRAY);
            Style pStyle = configurable.inRange(value + 1) ? Style.EMPTY.withClickEvent(runnable("/librget config " + config + " " + (value + 1))).withColor(Formatting.GREEN) : Style.EMPTY.withColor(Formatting.GRAY);

            t = applyStyle(Text.of("[-]"), mStyle);
            f = applyStyle(Text.of("[+]"), pStyle);

            m = " " + value + " ";

        } else {
            throw new RuntimeException("Unexpected type of configurable!");
        }

        text = Reflection.wrap("mc:text method_10852/append tc:t", mc, text, tc, t);
        text = Reflection.wrap("mc:text method_27693/append m", mc, text, m);
        return Reflection.wrap("mc:text method_10852/append tc:f", mc, text, tc, f);
    }

    public static ClickEvent runnable(String command) {
        if (Reflection.version(">= 1.21.5")) {
            return (ClickEvent) Reflection.wrap("[.class_2558$class_10609/.text.ClickEvent$RunCommand] String:command", command);
        } else {
            Class<?> action = (Class<?>) Reflection.wrap(".class_2558$class_2559/.text.ClickEvent$Action");
            return (ClickEvent) Reflection.wrap("ClickEvent action:[action field_11750/RUN_COMMAND] String:command", action, command);
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
