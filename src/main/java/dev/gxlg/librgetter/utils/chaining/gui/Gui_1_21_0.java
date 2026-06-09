package dev.gxlg.librgetter.utils.chaining.gui;

import dev.gxlg.librgetter.gui.widgets.list.CustomSelectionList;
import dev.gxlg.librgetter.gui.widgets.unified.list.UnifiedList;

public class Gui_1_21_0 extends Gui_1_20_5 {
    @Override
    public void refreshScrollAmount(UnifiedList list) {
        ((CustomSelectionList) list).clampScrollAmount();
    }
}
