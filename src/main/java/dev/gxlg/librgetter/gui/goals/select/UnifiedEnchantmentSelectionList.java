package dev.gxlg.librgetter.gui.goals.select;

import dev.gxlg.librgetter.gui.widgets.unified.list.UnifiedList;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.AbstractSelectionList$Entry;

public interface UnifiedEnchantmentSelectionList extends UnifiedList {
    void filterEntries(String filter);

    AbstractSelectionList$Entry getSelected();
}
