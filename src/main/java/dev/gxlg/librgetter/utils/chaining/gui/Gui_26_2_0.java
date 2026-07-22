package dev.gxlg.librgetter.utils.chaining.gui;

import dev.gxlg.versiont.gen.net.minecraft.client.Minecraft;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.screens.Screen;

public class Gui_26_2_0 extends Gui_1_21_9 {
    @Override
    public Screen getScreen(Minecraft minecraft) {
        return minecraft.getGuiField().getScreenField();
    }

    @Override
    public void setScreen(Minecraft minecraft, Screen screen) {
        minecraft.getGuiField().setScreen(screen);
    }
}
