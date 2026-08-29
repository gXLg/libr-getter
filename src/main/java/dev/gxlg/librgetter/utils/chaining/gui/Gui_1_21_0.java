package dev.gxlg.librgetter.utils.chaining.gui;

import dev.gxlg.librgetter.gui.lib.widgets.list.CustomSelectionList_1_20_3;
import dev.gxlg.librgetter.gui.lib.widgets.unified.list.UnifiedListWidget;

public class Gui_1_21_0 extends Gui_1_20_5 {
    @Override
    public void refreshScrollAmount(UnifiedListWidget list) {
        ((CustomSelectionList_1_20_3) list).clampScrollAmount();
    }
}
