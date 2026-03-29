package dev.gxlg.librgetter.utils.chaining.texts;

import dev.gxlg.librgetter.utils.types.messages.Message;
import dev.gxlg.versiont.api.V;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.ClickEvent;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Component;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.HoverEvent;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.MutableComponent;
import dev.gxlg.versiont.gen.net.minecraft.resources.Identifier;

public class Texts {
    private static final Base implementation;

    static {
        if (V.lower("1.19")) {
            implementation = new Texts_1_17_0();
        } else if (V.lower("1.19.4")) {
            implementation = new Texts_1_19_0();
        } else if (V.lower("1.21.5")) {
            implementation = new Texts_1_19_4();
        } else if (V.lower("26.1")) {
            implementation = new Texts_1_21_5();
        } else {
            implementation = new Texts_26_1_0();
        }
    }

    public static void sendMessage(Message message) {
        implementation.sendMessage(message);
    }

    public static void sendMessage(Message message, boolean actionbar) {
        implementation.sendMessage(message, actionbar);
    }

    public static MutableComponent literal(String text) {
        return implementation.literal(text);
    }

    public static MutableComponent translatable(String message, Object... args) {
        return implementation.translatable(message, args);
    }

    public static ClickEvent runnable(String command) {
        return implementation.runnable(command);
    }

    public static HoverEvent hoverable(Component text) {
        return implementation.hoverable(text);
    }

    public static String translateIdentifier(IdentifierType type, Identifier id) {
        return implementation.translateIdentifier(type, id);
    }

    public static ClickEvent paging(int page) {
        return implementation.paging(page);
    }

    public enum IdentifierType {
        ENCHANTMENT("enchantment");

        private final String id;

        IdentifierType(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }

    public abstract static class Base {
        public abstract void sendMessage(Message message);

        public abstract void sendMessage(Message message, boolean actionbar);

        public abstract MutableComponent literal(String text);

        public abstract MutableComponent translatable(String message, Object... args);

        public abstract String translateIdentifier(IdentifierType type, Identifier id);

        public abstract ClickEvent runnable(String command);

        public abstract ClickEvent paging(int page);

        public abstract HoverEvent hoverable(Component text);
    }
}
