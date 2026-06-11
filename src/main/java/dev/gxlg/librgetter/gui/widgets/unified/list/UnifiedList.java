package dev.gxlg.librgetter.gui.widgets.unified.list;

import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.AbstractSelectionList$Entry;

public interface UnifiedList {
    @SuppressWarnings("UnusedReturnValue")
    boolean removeEntry(AbstractSelectionList$Entry entry);

    void removeEntry2(AbstractSelectionList$Entry entry);

    AbstractSelectionList$Entry getSelected();
}
