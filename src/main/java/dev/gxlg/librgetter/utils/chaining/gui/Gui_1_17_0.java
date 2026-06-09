package dev.gxlg.librgetter.utils.chaining.gui;

import dev.gxlg.librgetter.gui.widgets.unified.list.UnifiedList;
import dev.gxlg.versiont.gen.com.mojang.blaze3d.vertex.PoseStack;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.Font;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.GuiGraphicsExtractor;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.ObjectSelectionList$Entry;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.screens.inventory.BookViewScreen$BookAccess;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.screens.inventory.BookViewScreen$BookAccessI;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Component;

import java.util.List;

public class Gui_1_17_0 extends Gui.Base {
    @Override
    public BookViewScreen$BookAccess createBookAccess(List<Component> list) {
        int pageCount = list.size();
        return new BookViewScreen$BookAccessI() {
            @Override
            public int getPageCount() {
                return pageCount;
            }

            @Override
            public Component getPage(int index) {
                if (index < 0 || index >= pageCount) {
                    return Component.nullToEmpty("");
                }
                return list.get(index);
            }
        }.asBookViewScreen$BookAccess();
    }

    @Override
    public void extractText(PoseStack poseStack, GuiGraphicsExtractor guiGraphics, Font font, String str, int x, int y, int color) {
        font.draw(poseStack, str, x, y, color);
    }

    @Override
    public void extractText(PoseStack poseStack, GuiGraphicsExtractor guiGraphics, Font font, Component str, int x, int y, int color) {
        font.draw(poseStack, str, x, y, color);
    }

    @Override
    public void removeListEntry(UnifiedList list, ObjectSelectionList$Entry entry) {
        list.removeEntry(entry);
    }

    @Override
    public void refreshScrollAmount(UnifiedList list) {
        // this method is not needed, as the scrolling is clamped per default in versions [1.17, 1.21)
    }
}
