package dev.gxlg.librgetter.utils.chaining.gui;

import dev.gxlg.librgetter.gui.widgets.unified.list.UnifiedList;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.ObjectSelectionList$Entry;
import org.jetbrains.annotations.UnknownNullability;

public class Gui_1_21_9 extends Gui_1_21_6 {
    @Override
    public void removeListEntry(@UnknownNullability UnifiedList list, ObjectSelectionList$Entry entry) {
        list.removeEntry2(entry);
    }
}
