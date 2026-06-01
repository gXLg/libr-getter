package dev.gxlg.librgetter.utils.chaining.gui;

import dev.gxlg.versiont.gen.net.minecraft.client.gui.Font;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.GuiGraphicsExtractor;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Component;

public class Gui_1_21_6 extends Gui_1_20_5 {
    @Override
    public void extractText(GuiGraphicsExtractor guiGraphics, Font font, String str, int x, int y, int color) {
        guiGraphics.text(font, str, x, y, color);
    }

    @Override
    public void extractText(GuiGraphicsExtractor guiGraphics, Font font, Component str, int x, int y, int color) {
        guiGraphics.text(font, str, x, y, color);
    }
}
