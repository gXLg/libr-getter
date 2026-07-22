package dev.gxlg.librgetter.utils.chaining.gui;

import dev.gxlg.librgetter.gui.widgets.unified.list.UnifiedList;
import dev.gxlg.versiont.api.V;
import dev.gxlg.versiont.gen.com.mojang.blaze3d.vertex.PoseStack;
import dev.gxlg.versiont.gen.net.minecraft.client.Minecraft;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.Font;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.GuiGraphicsExtractor;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.ObjectSelectionList$Entry;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.screens.Screen;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.screens.inventory.BookViewScreen$BookAccess;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Component;

import java.util.List;

public class Gui {
    private static final Base implementation;

    static {
        if (!V.lower("26.2")) {
            implementation = new Gui_26_2_0();
        } else if (!V.lower("1.21.9")) {
            implementation = new Gui_1_21_9();
        } else if (!V.lower("1.21.6")) {
            implementation = new Gui_1_21_6();
        } else if (!V.lower("1.21.4")) {
            implementation = new Gui_1_21_4();
        } else if (!V.lower("1.21")) {
            implementation = new Gui_1_21_0();
        } else if (!V.lower("1.20.5")) {
            implementation = new Gui_1_20_5();
        } else if (!V.lower("1.20")) {
            implementation = new Gui_1_20_0();
        } else {
            implementation = new Gui_1_17_0();
        }
    }

    public static BookViewScreen$BookAccess createBookAccess(List<Component> list) {
        return implementation.createBookAccess(list);
    }

    public static void extractText(PoseStack poseStack, GuiGraphicsExtractor guiGraphics, Font font, String str, int x, int y, int color) {
        implementation.extractText(poseStack, guiGraphics, font, str, x, y, color);
    }

    public static void extractText(PoseStack poseStack, GuiGraphicsExtractor guiGraphics, Font font, Component str, int x, int y, int color) {
        implementation.extractText(poseStack, guiGraphics, font, str, x, y, color);
    }

    public static void removeListEntry(UnifiedList list, ObjectSelectionList$Entry entry) {
        implementation.removeListEntry(list, entry);
    }

    public static void refreshScrollAmount(UnifiedList list) {
        implementation.refreshScrollAmount(list);
    }

    public static Screen getScreen(Minecraft minecraft) {
        return implementation.getScreen(minecraft);
    }

    public static void setScreen(Minecraft minecraft, Screen screen) {
        implementation.setScreen(minecraft, screen);
    }

    public abstract static class Base {
        public abstract BookViewScreen$BookAccess createBookAccess(List<Component> list);

        public abstract void extractText(PoseStack poseStack, GuiGraphicsExtractor guiGraphics, Font font, String str, int x, int y, int color);

        public abstract void extractText(PoseStack poseStack, GuiGraphicsExtractor guiGraphics, Font font, Component str, int x, int y, int color);

        public abstract void removeListEntry(UnifiedList list, ObjectSelectionList$Entry entry);

        public abstract void refreshScrollAmount(UnifiedList list);

        public abstract Screen getScreen(Minecraft minecraft);

        public abstract void setScreen(Minecraft minecraft, Screen screen);
    }
}
