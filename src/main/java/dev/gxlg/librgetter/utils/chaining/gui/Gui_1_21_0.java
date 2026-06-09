package dev.gxlg.librgetter.utils.chaining.gui;

import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.AbstractSelectionList;

public class Gui_1_21_0 extends Gui_1_20_5 {
    @Override
    public void refreshScrollAmount(AbstractSelectionList list) {
        list.clampScrollAmount();
    }
}
