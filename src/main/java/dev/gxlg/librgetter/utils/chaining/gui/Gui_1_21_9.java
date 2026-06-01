package dev.gxlg.librgetter.utils.chaining.gui;

import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.ObjectSelectionList;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.ObjectSelectionList$Entry;

public class Gui_1_21_9 extends Gui_1_21_6 {
    @Override
    public void removeListEntry(ObjectSelectionList list, ObjectSelectionList$Entry entry) {
        list.removeEntry2(entry);
    }
}
