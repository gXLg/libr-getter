package dev.gxlg.librgetter.utils.chaining.gui;

import dev.gxlg.versiont.api.V;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.Font;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.GuiGraphicsExtractor;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.ObjectSelectionList;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.ObjectSelectionList$Entry;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.screens.inventory.BookViewScreen$BookAccess;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Component;

import java.util.List;

public class Gui {
    private static final Base implementation;

    static {
        if (V.lower("1.20.5")) {
            implementation = new Gui_1_17_0();
        } else if (V.lower("1.21.6")) {
            implementation = new Gui_1_20_5();
        } else if (V.lower("1.21.9")) {
            implementation = new Gui_1_21_6();
        } else {
            implementation = new Gui_1_21_9();
        }
    }

    public static BookViewScreen$BookAccess createBookAccess(List<Component> list) {
        return implementation.createBookAccess(list);
    }

    public static void extractText(GuiGraphicsExtractor guiGraphics, Font font, String str, int x, int y, int color) {
        implementation.extractText(guiGraphics, font, str, x, y, color);
    }

    public static void extractText(GuiGraphicsExtractor guiGraphics, Font font, Component str, int x, int y, int color) {
        implementation.extractText(guiGraphics, font, str, x, y, color);
    }

    public static void removeListEntry(ObjectSelectionList list, ObjectSelectionList$Entry entry) {
        implementation.removeListEntry(list, entry);
    }

    public abstract static class Base {
        public abstract BookViewScreen$BookAccess createBookAccess(List<Component> list);

        public abstract void extractText(GuiGraphicsExtractor guiGraphics, Font font, String str, int x, int y, int color);

        public abstract void extractText(GuiGraphicsExtractor guiGraphics, Font font, Component str, int x, int y, int color);

        public abstract void removeListEntry(ObjectSelectionList list, ObjectSelectionList$Entry entry);
    }
}
