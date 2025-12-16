package dev.gxlg.librgetter.utils.reflection;

import dev.gxlg.librgetter.Config;
import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.multiversion.C;
import dev.gxlg.librgetter.multiversion.R;
import dev.gxlg.librgetter.multiversion.V;
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

    private static final R.RClass fcs;

    static {
        if (!V.lower("1.19")) {
            fcs = R.clz("net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource");
        } else {
            fcs = R.clz("net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource");
        }
    }

    private static Object translatable(String message, Object... args) {
        Class<?> arr = Object[].class;
        if (!V.lower("1.19")) {
            return C.Text.mthd("method_43469/translatable", String.class, arr).invk(message, args);
        } else {
            return R.clz("net.minecraft.class_2588/net.minecraft.text.TranslatableText").constr(String.class, arr).newInst(message, args).self();
        }
    }

    private static Object applyStyle(Object text, Style style) {
        Object copy = C.Text.inst(text).mthd("method_27662/copy").invk();
        C.MutableText.inst(copy).mthd("method_10862/setStyle", Style.class).invk(style);
        return copy;
    }

    private static Object literal(String message) {
        Object m = Text.of(message);
        return C.Text.inst(m).mthd("method_27662/copy").invk();
    }

    public static void sendError(Object source, String message, Object... args) {
        Object text = translatable(message, args);
        fcs.inst(source).mthd("sendError", C.Text).invk(text);
    }

    public static void sendFound(Object source, EnchantmentTrade enchant, int counter) {
        Object text = translatable("librgetter.found", enchant, counter, enchant.price());
        text = C.MutableText.inst(text).mthd("method_27692/formatted", Formatting.class).invk(Formatting.GREEN);

        if (!LibrGetter.config.removeGoal) {
            text = C.MutableText.inst(text).mthd("method_27693/append", String.class).invk(" ");
            Object rem = translatable("librgetter.remove");
            Style style = Style.EMPTY.withClickEvent(runnable("/librget remove \"" + enchant.id() + "\" " + enchant.lvl())).withColor(Formatting.YELLOW);
            C.MutableText.inst(rem).mthd("method_10862/setStyle", Style.class).invk(style);
            text = C.MutableText.inst(text).mthd("method_10852/append", C.Text).invk(rem);
        }
        fcs.inst(source).mthd("sendFeedback", C.Text).invk(text);
    }

    public static void sendFeedback(Object source, String message, Formatting format, Object... args) {
        Object text = translatable(message, args);
        if (format != null) {
            text = C.MutableText.inst(text).mthd("method_27692/formatted", Formatting.class).invk(format);
        }
        fcs.inst(source).mthd("sendFeedback", C.Text).invk(text);
    }

    public static void sendMessage(ClientPlayerEntity player, String message, Object... args) {
        if (LibrGetter.config.logMode == LogMode.NONE) return;
        boolean ab = LibrGetter.config.logMode == LogMode.ACTIONBAR;
        Object text = translatable(message, args);
        R.clz(ClientPlayerEntity.class).inst(player).mthd("method_7353/sendMessage", C.Text, boolean.class).invk(text, ab);
    }

    public static void newVersion(ClientPlayerEntity player, String message, String hover) {
        Object text = translatable(message);
        Text hov = Text.of(hover);
        Style style = Style.EMPTY.withHoverEvent(hoverable(hov));

        C.MutableText.inst(text).mthd("method_10862/setStyle", Style.class).invk(style);
        R.clz(ClientPlayerEntity.class).inst(player).mthd("method_7353/sendMessage", C.Text, boolean.class).invk(text, false);
    }

    public static void list(Object source) {
        Object text = translatable("librgetter.list");
        Object rem = translatable("librgetter.remove");
        for (EnchantmentTrade l : LibrGetter.config.goals) {
            String line = "\n- " + l + " (" + l.price() + ") ";
            text = C.MutableText.inst(text).mthd("method_27693/append", String.class).invk(line);
            Style style = Style.EMPTY.withClickEvent(runnable("/librget remove \"" + l.id() + "\" " + l.lvl())).withColor(Formatting.YELLOW);
            Object remc = applyStyle(rem, style);
            text = C.MutableText.inst(text).mthd("method_10852/append", C.Text).invk(remc);
        }
        fcs.inst(source).mthd("sendFeedback", C.Text).invk(text);
    }

    public static Object bookMainPage(Map<String, Integer> categories) {
        Object text = Text.of("");
        Object lg = Text.of("LibrGetter " + LibrGetter.getVersion() + "\n");
        lg = C.Text.inst(lg).mthd("method_27662/copy").invk();
        lg = C.MutableText.inst(lg).mthd("method_27692/formatted", Formatting.class).invk(Formatting.GREEN);
        text = C.MutableText.inst(text).mthd("method_10852/append", C.Text).invk(lg);
        Object s = translatable("librgetter.menu");
        text = C.MutableText.inst(text).mthd("method_10852/append", C.Text).invk(s);
        String nl = "\n";
        String star = "\n* ";
        text = C.MutableText.inst(text).mthd("method_27693/append", String.class).invk(nl);
        for (String cat : Config.CATEGORIES) {
            text = C.MutableText.inst(text).mthd("method_27693/append", String.class).invk(star);
            s = applyStyle(translatable("librgetter.category." + cat), Style.EMPTY.withClickEvent(paging(categories.get(cat) + 1)));
            text = C.MutableText.inst(text).mthd("method_10852/append", C.Text).invk(s);
        }
        return text;
    }

    public static Object bookTitle(String category) {
        Object text = Text.of("");
        Object lg = Text.of("LibrGetter " + LibrGetter.getVersion() + "\n");
        lg = C.Text.inst(lg).mthd("method_27662/copy").invk();
        lg = C.MutableText.inst(lg).mthd("method_27692/formatted", Formatting.class).invk(Formatting.DARK_GREEN);
        text = C.MutableText.inst(text).mthd("method_10852/append", C.Text).invk(lg);
        Object s = applyStyle(Text.of("↩"), Style.EMPTY.withClickEvent(paging(1)));
        text = C.MutableText.inst(text).mthd("method_10852/append", C.Text).invk(s);
        s = literal(" ");
        text = C.MutableText.inst(text).mthd("method_10852/append", C.Text).invk(s);
        s = translatable("librgetter.category." + category);
        return C.MutableText.inst(text).mthd("method_10852/append", C.Text).invk(s);
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
        text = C.MutableText.inst(text).mthd("method_27693/append", String.class).invk(nl + nl);
        text = C.MutableText.inst(text).mthd("method_10852/append", C.Text).invk(c);

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
            text = C.MutableText.inst(text).mthd("method_10852/append", C.Text).invk(s);
            s = applyStyle(Text.of("↩"), r.withColor(black));
            text = C.MutableText.inst(text).mthd("method_10852/append", C.Text).invk(s);
        }
        text = C.MutableText.inst(text).mthd("method_27693/append", String.class).invk(nl);


        text = C.MutableText.inst(text).mthd("method_10852/append", C.Text).invk(x);
        text = C.MutableText.inst(text).mthd("method_10852/append", C.Text).invk(y);
        return C.MutableText.inst(text).mthd("method_10852/append", C.Text).invk(z);
    }

    public static ClickEvent runnable(String command) {
        if (!V.lower("1.21.5")) {
            return (ClickEvent) R.clz("net.minecraft.class_2558$class_10609/net.minecraft.text.ClickEvent$RunCommand").constr(String.class).newInst(command).self();
        } else {
            return (ClickEvent) R.clz(ClickEvent.class).constr(C.ClickEvent$Action, String.class).newInst(C.ClickEvent$Action.fld("field_11750/RUN_COMMAND").get(), command).self();
        }
    }

    public static ClickEvent paging(int page) {
        if (!V.lower("1.21.5")) {
            return (ClickEvent) R.clz("net.minecraft.class_2558$class_10605/net.minecraft.text.ClickEvent$ChangePage").constr(int.class).newInst(page).self();
        } else {
            String pageString = String.valueOf(page);
            return (ClickEvent) R.clz(ClickEvent.class).constr(C.ClickEvent$Action, String.class).newInst(C.ClickEvent$Action.fld("field_11748/CHANGE_PAGE").get(), pageString).self();
        }
    }

    public static HoverEvent hoverable(Object text) {
        if (!V.lower("1.21.5")) {
            return (HoverEvent) R.clz("net.minecraft.class_2568$class_10613/net.minecraft.text.HoverEvent$ShowText").constr(C.Text).newInst(text).self();
        } else {
            return (HoverEvent) R.clz(HoverEvent.class).constr(C.HoverEvent$Action, Object.class).newInst(C.HoverEvent$Action.fld("field_24342/SHOW_TEXT").get(), text).self();
        }
    }
}
