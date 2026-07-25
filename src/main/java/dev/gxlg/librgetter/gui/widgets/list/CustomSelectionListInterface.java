package dev.gxlg.librgetter.gui.widgets.list;

import dev.gxlg.librgetter.gui.widgets.unified.list.UnifiedWidgetList;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.AbstractSelectionList$Entry;
import dev.gxlg.versiont.gen.net.minecraft.client.input.MouseButtonEvent;

import java.util.List;

public interface CustomSelectionListInterface extends UnifiedWidgetList {
    List<CustomSelectionListEntry> getCustomEntries();

    void clearEntries();

    void replaceEntries(List<AbstractSelectionList$Entry> entries);

    @SuppressWarnings("UnusedReturnValue")
    int addEntry(AbstractSelectionList$Entry entry);

    @SuppressWarnings("unused")
    boolean mouseClicked(MouseButtonEvent event, boolean isDoubleClick);

    @SuppressWarnings("unused")
    boolean mouseClicked(double mouseX, double mouseY, int button);

    int getXField();

    int getYField();

    int getWidthField();

    int getHeightField();

    void setSelected(AbstractSelectionList$Entry entry);

    default boolean clickElement(double mouseX, double mouseY, int button) {
        if (button != 0) {
            return false;
        }
        int rowHalf = this.getRowWidth() / 2;
        int center = this.getXField() + this.getWidthField() / 2;
        if (mouseX < center - rowHalf || mouseX > center + rowHalf) {
            return false;
        }
        int top = getYField();
        int bottom = top + getHeightField();
        if (mouseY < top || mouseY > bottom) {
            return false;
        }
        for (CustomSelectionListEntry entry : getCustomEntries()) {
            if (entry.isMouseOver(mouseX, mouseY)) {
                setSelected(entry);
                return true;
            }
        }
        return false;
    }

    int getRowWidth();
}
