package dev.gxlg.librgetter.utils.chaining.texts;

import dev.gxlg.librgetter.utils.types.messages.translatable.TranslatableMessage;
import dev.gxlg.versiont.api.V;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.ClickEvent;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Component;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.HoverEvent;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.MutableComponent;
import dev.gxlg.versiont.gen.net.minecraft.resources.Identifier;

public class Texts {
    private static Base implementation = null;

    public static void sendTranslatable(TranslatableMessage translatableMessage) {
        getImpl().sendTranslatable(translatableMessage);
    }

    public static void sendTranslatable(TranslatableMessage translatableMessage, boolean actionbar) {
        getImpl().sendTranslatable(translatableMessage, actionbar);
    }

    public static MutableComponent literal(String text) {
        return getImpl().literal(text);
    }

    public static MutableComponent translatable(String message, Object... args) {
        return getImpl().translatable(message, args);
    }

    public static ClickEvent runnable(String command) {
        return getImpl().runnable(command);
    }

    public static HoverEvent hoverable(Component text) {
        return getImpl().hoverable(text);
    }

    public static String translateIdentifier(IdentifierType type, Identifier id) {
        return getImpl().translateIdentifier(type, id);
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

    public static ClickEvent paging(int page) {
        return getImpl().paging(page);
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
        public abstract void sendTranslatable(TranslatableMessage translatableMessage);

        public abstract void sendTranslatable(TranslatableMessage translatableMessage, boolean actionbar);

        public abstract MutableComponent literal(String text);

        public abstract MutableComponent translatable(String message, Object... args);

        public abstract String translateIdentifier(IdentifierType type, Identifier id);

        public abstract ClickEvent runnable(String command);

        public abstract ClickEvent paging(int page);

        public abstract HoverEvent hoverable(Component text);
    }
}
