package com.gxlg.librgetter.utils.reflection;

import com.gxlg.librgetter.Config;
import com.gxlg.librgetter.LibrGetter;
import com.gxlg.librgetter.utils.MultiVersion;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class Texts {

    private static final Class<?> tc = Reflection.clazz("net.minecraft.class_2561", "net.minecraft.text.Text");
    private static final Class<?> mc = Reflection.clazz("net.minecraft.class_5250", "net.minecraft.text.MutableText");

    public static void sendError(Object source, String message, Object... args) {
        Object[] argv = new Object[]{message, args};
        Class<?>[] argcl = new Class[]{String.class, Object[].class};

        Class<?> fcs;
        Object text;
        if (MultiVersion.isApiLevel(MultiVersion.ApiLevel.API_COMMAND_V2)) {
            fcs = Reflection.clazz("net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource");
            text = Reflection.invokeMethod(tc, null, argv, argcl, "method_43469", "translatable");
        } else {
            fcs = Reflection.clazz("net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource");
            Class<?> tra = Reflection.clazz("net.minecraft.class_2588", "net.minecraft.text.TranslatableText");
            text = Reflection.construct(tra, argv, argcl);
        }
        Reflection.invokeMethod(fcs, source, new Object[]{text}, new Class[]{tc}, "sendError");
    }

    public static void sendFound(Object source, Config.Enchantment enchant, int counter) {
        Object[] argv = new Object[]{"librgetter.found", new Object[]{enchant, counter, enchant.price}};
        Class<?>[] argcl = new Class[]{String.class, Object[].class};

        Class<?> fcs;
        Object text;
        if (MultiVersion.isApiLevel(MultiVersion.ApiLevel.API_COMMAND_V2)) {
            fcs = Reflection.clazz("net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource");
            text = Reflection.invokeMethod(tc, null, argv, argcl, "method_43469", "translatable");
        } else {
            fcs = Reflection.clazz("net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource");
            Class<?> tra = Reflection.clazz("net.minecraft.class_2588", "net.minecraft.text.TranslatableText");
            text = Reflection.construct(tra, argv, argcl);
        }
        text = Reflection.invokeMethod(mc, text, new Object[]{Formatting.GREEN}, "method_27692", "formatted");

        if (!LibrGetter.config.removeGoal) {
            text = Reflection.invokeMethod(mc, text, new Object[]{" "}, "method_27693", "append");
            Object rem;
            if (MultiVersion.isApiLevel(MultiVersion.ApiLevel.API_COMMAND_V2)) {
                rem = Reflection.invokeMethod(tc, null, new Object[]{"librgetter.remove"}, "method_43471", "translatable");
            } else {
                Class<?> tra = Reflection.clazz("net.minecraft.class_2588", "net.minecraft.text.TranslatableText");
                rem = Reflection.construct(tra, new Object[]{"librgetter.remove", new Object[]{}}, String.class, Object[].class);
            }

            Style style = Style.EMPTY.withClickEvent(runnable("/librget remove \"" + enchant.id + "\" " + enchant.lvl)).withColor(Formatting.YELLOW);

            Reflection.invokeMethod(mc, rem, new Object[]{style}, "method_10862", "setStyle");
            text = Reflection.invokeMethod(mc, text, new Object[]{rem}, new Class[]{tc}, "method_10852", "append");
        }

        Reflection.invokeMethod(fcs, source, new Object[]{text}, new Class[]{tc}, "sendFeedback");
    }

    public static void sendFeedback(Object source, String message, Formatting format, Object... args) {
        Object[] argv = new Object[]{message, args};
        Class<?>[] argcl = new Class[]{String.class, Object[].class};

        Class<?> fcs;
        Object text;
        if (MultiVersion.isApiLevel(MultiVersion.ApiLevel.API_COMMAND_V2)) {
            fcs = Reflection.clazz("net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource");
            text = Reflection.invokeMethod(tc, null, argv, argcl, "method_43469", "translatable");
        } else {
            fcs = Reflection.clazz("net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource");
            Class<?> tra = Reflection.clazz("net.minecraft.class_2588", "net.minecraft.text.TranslatableText");
            text = Reflection.construct(tra, argv, argcl);
        }
        if (format != null) {
            text = Reflection.invokeMethod(mc, text, new Object[]{format}, "method_27692", "formatted");
        }
        Reflection.invokeMethod(fcs, source, new Object[]{text}, new Class[]{tc}, "sendFeedback");
    }

    public static void sendMessage(ClientPlayerEntity player, String message, boolean ab, Object... args) {
        Object[] argv = new Object[]{message, args};
        Class<?>[] argcl = new Class[]{String.class, Object[].class};

        Object text;
        if (MultiVersion.isApiLevel(MultiVersion.ApiLevel.API_COMMAND_V2)) {
            text = Reflection.invokeMethod(tc, null, argv, argcl, "method_43469", "translatable");
        } else {
            Class<?> tra = Reflection.clazz("net.minecraft.class_2588", "net.minecraft.text.TranslatableText");
            text = Reflection.construct(tra, argv, argcl);
        }
        Reflection.invokeMethod(ClientPlayerEntity.class, player, new Object[]{text, ab}, new Class[]{tc, boolean.class}, "method_7353", "sendMessage");
    }

    public static void newVersion(ClientPlayerEntity player, String message, String hover) {
        Object[] argv = new Object[]{"librgetter.version", new Object[]{message}};
        Class<?>[] argcl = new Class[]{String.class, Object[].class};

        Object msg;
        if (MultiVersion.isApiLevel(MultiVersion.ApiLevel.API_COMMAND_V2)) {
            msg = Reflection.invokeMethod(tc, null, argv, argcl, "method_43469", "translatable");
        } else {
            Class<?> tra = Reflection.clazz("net.minecraft.class_2588", "net.minecraft.text.TranslatableText");
            msg = Reflection.construct(tra, argv, argcl);
        }
        Text hov = Text.of(hover);

        Style style = Style.EMPTY.withHoverEvent(hoverable(hov));
        Reflection.invokeMethod(mc, msg, new Object[]{style}, "method_10862", "setStyle");

        Reflection.invokeMethod(ClientPlayerEntity.class, player, new Object[]{msg, false}, new Class[]{tc, boolean.class}, "method_7353", "sendMessage");
    }

    public static void list(Object source) {
        Class<?> fcs;
        Object text;
        Object rem;
        if (MultiVersion.isApiLevel(MultiVersion.ApiLevel.API_COMMAND_V2)) {
            fcs = Reflection.clazz("net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource");
            text = Reflection.invokeMethod(tc, null, new Object[]{"librgetter.list"}, "method_43471", "translatable");
            rem = Reflection.invokeMethod(tc, null, new Object[]{"librgetter.remove"}, "method_43471", "translatable");
        } else {
            fcs = Reflection.clazz("net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource");
            Class<?> tra = Reflection.clazz("net.minecraft.class_2588", "net.minecraft.text.TranslatableText");
            text = Reflection.construct(tra, new Object[]{"librgetter.list", new Object[]{}}, String.class, Object[].class);
            rem = Reflection.construct(tra, new Object[]{"librgetter.remove", new Object[]{}}, String.class, Object[].class);
        }
        for (Config.Enchantment l : LibrGetter.config.goals) {
            text = Reflection.invokeMethod(mc, text, new Object[]{"\n- " + l + " (" + l.price + ") "}, "method_27693", "append");
            Style style = Style.EMPTY.withClickEvent(runnable("/librget remove \"" + l.id + "\" " + l.lvl)).withColor(Formatting.YELLOW);
            Object remC = Reflection.invokeMethod(tc, rem, null, "method_27662", "copy");
            Reflection.invokeMethod(mc, remC, new Object[]{style}, "method_10862", "setStyle");
            text = Reflection.invokeMethod(mc, text, new Object[]{remC}, new Class[]{tc}, "method_10852", "append");
        }
        Reflection.invokeMethod(fcs, source, new Object[]{text}, new Class[]{tc}, "sendFeedback");
    }

    public static Object bookTitle() {
        Object text = Text.of("");

        Object lg = Text.of("LibrGetter " + LibrGetter.getVersion() + "\n");
        lg = Reflection.invokeMethod(tc, lg, null, "method_27662", "copy");
        lg = Reflection.invokeMethod(mc, lg, new Object[]{Formatting.DARK_GREEN}, "method_27692", "formatted");
        text = Reflection.invokeMethod(mc, text, new Object[]{lg}, new Class[]{tc}, "method_10852", "append");

        Object menu;
        if (MultiVersion.isApiLevel(MultiVersion.ApiLevel.API_COMMAND_V2)) {
            menu = Reflection.invokeMethod(tc, null, new Object[]{"librgetter.menu"}, "method_43471", "translatable");
        } else {
            Class<?> tra = Reflection.clazz("net.minecraft.class_2588", "net.minecraft.text.TranslatableText");
            menu = Reflection.construct(tra, new Object[]{"librgetter.menu", new Object[]{}}, String.class, Object[].class);
        }
        text = Reflection.invokeMethod(mc, text, new Object[]{menu}, new Class[]{tc}, "method_10852", "append");
        return text;
    }

    public static Object bookEntry(Object text, Config.Configurable<?> configurable) {
        String config = configurable.name();
        String showName = config.startsWith("_") ? "+ " + config.substring(1) : config;

        Object name;
        if (MultiVersion.isApiLevel(MultiVersion.ApiLevel.API_COMMAND_V2)) {
            name = Reflection.invokeMethod(tc, null, new Object[]{"librgetter.config." + config}, "method_43471", "translatable");
        } else {
            Class<?> tra = Reflection.clazz("net.minecraft.class_2588", "net.minecraft.text.TranslatableText");
            name = Reflection.construct(tra, new Object[]{"librgetter.config." + config, new Object[]{}}, String.class, Object[].class);
        }

        Style cStyle = Style.EMPTY.withHoverEvent(hoverable(name));
        Object c = Text.of(showName);
        c = Reflection.invokeMethod(tc, c, null, "method_27662", "copy");
        Reflection.invokeMethod(mc, c, new Object[]{cStyle}, "method_10862", "setStyle");

        text = Reflection.invokeMethod(mc, text, new Object[]{"\n\n"}, "method_27693", "append");
        text = Reflection.invokeMethod(mc, text, new Object[]{c}, new Class[]{tc}, "method_10852", "append");
        text = Reflection.invokeMethod(mc, text, new Object[]{"\n"}, "method_27693", "append");

        Object t, f;
        String m;

        if (configurable.type() == Boolean.class) {
            boolean value = (boolean) configurable.get();
            Style tStyle = Style.EMPTY.withClickEvent(runnable("/librget config " + config + " true")).withColor(Formatting.GREEN).withUnderline(value);
            Style fStyle = Style.EMPTY.withClickEvent(runnable("/librget config " + config + " false")).withColor(Formatting.RED).withUnderline(!value);

            t = Text.of("[true]");
            t = Reflection.invokeMethod(tc, t, null, "method_27662", "copy");
            Reflection.invokeMethod(mc, t, new Object[]{tStyle}, "method_10862", "setStyle");

            f = Text.of("[false]");
            f = Reflection.invokeMethod(tc, f, null, "method_27662", "copy");
            Reflection.invokeMethod(mc, f, new Object[]{fStyle}, "method_10862", "setStyle");

            m = " ";

        } else if (configurable.type() == Integer.class) {
            int value = (int) configurable.get();
            Style mStyle = configurable.inRange(value - 1) ? Style.EMPTY.withClickEvent(runnable("/librget config " + config + " " + (value - 1))).withColor(Formatting.RED) : Style.EMPTY.withColor(Formatting.GRAY);
            Style pStyle = configurable.inRange(value + 1) ? Style.EMPTY.withClickEvent(runnable("/librget config " + config + " " + (value + 1))).withColor(Formatting.GREEN) : Style.EMPTY.withColor(Formatting.GRAY);

            t = Text.of("[-]");
            t = Reflection.invokeMethod(tc, t, null, "method_27662", "copy");
            Reflection.invokeMethod(mc, t, new Object[]{mStyle}, "method_10862", "setStyle");

            f = Text.of("[+]");
            f = Reflection.invokeMethod(tc, f, null, "method_27662", "copy");
            Reflection.invokeMethod(mc, f, new Object[]{pStyle}, "method_10862", "setStyle");

            m = " " + value + " ";

        } else {
            throw new RuntimeException("Unexpected type of configurable!");
        }

        text = Reflection.invokeMethod(mc, text, new Object[]{t}, new Class[]{tc}, "method_10852", "append");
        text = Reflection.invokeMethod(mc, text, new Object[]{m}, "method_27693", "append");
        text = Reflection.invokeMethod(mc, text, new Object[]{f}, new Class[]{tc}, "method_10852", "append");

        return text;
    }

    public static ClickEvent runnable(String command) {
        if (MultiVersion.isApiLevel(MultiVersion.ApiLevel.MORE_ABSTRACTION)) {
            Class<?> run = Reflection.clazz("net.minecraft.class_2558$class_10609", "net.minecraft.text.ClickEvent$RunCommand");
            return (ClickEvent) Reflection.construct(run, new Object[]{command}, String.class);
        } else {
            Class<?> action = Reflection.clazz("net.minecraft.class_2558$class_2559", "net.minecraft.text.ClickEvent$Action");
            Object event = Reflection.field(action, null, "field_11750", "RUN_COMMAND");
            return (ClickEvent) Reflection.construct(ClickEvent.class, new Object[]{event, command}, action, String.class);
        }
    }

    public static HoverEvent hoverable(Object text) {
        if (MultiVersion.isApiLevel(MultiVersion.ApiLevel.MORE_ABSTRACTION)) {
            Class<?> hover = Reflection.clazz("net.minecraft.class_2568$class_10613", "net.minecraft.text.HoverEvent$ShowText");
            return (HoverEvent) Reflection.construct(hover, new Object[]{text}, Text.class);
        } else {
            Class<?> action = Reflection.clazz("net.minecraft.class_2568$class_5247", "net.minecraft.text.HoverEvent$Action");
            Object event = Reflection.field(action, null, "field_24342", "SHOW_TEXT");
            return (HoverEvent) Reflection.construct(HoverEvent.class, new Object[]{event, text}, action, Object.class);
        }
    }
}
