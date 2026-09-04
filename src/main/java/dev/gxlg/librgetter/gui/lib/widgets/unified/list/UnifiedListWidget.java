package dev.gxlg.librgetter.gui.lib.widgets.unified.list;

import dev.gxlg.librgetter.gui.lib.widgets.unified.UnifiedWidget;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.AbstractSelectionList$Entry;

import java.util.List;

public interface UnifiedListWidget extends UnifiedWidget {
    void setX0Field(int x0);

    void setY0Field(int y0);

    int getXField();

    @Override
    default void setXField(int x) {
        setX0Field(x);
    }

    int getYField();

    @Override
    default void setYField(int y) {
        setY0Field(y);
    }

    int getWidthField();

    int getHeightField();

    int getRowWidth();

    List<AbstractSelectionList$Entry> children();

    void clearEntries();

    void replaceEntries(List<AbstractSelectionList$Entry> entries);

    @SuppressWarnings("UnusedReturnValue")
    int addEntry(AbstractSelectionList$Entry entry);

    @SuppressWarnings("UnusedReturnValue")
    boolean removeEntry(AbstractSelectionList$Entry entry);

    void removeEntry2(AbstractSelectionList$Entry entry);

    AbstractSelectionList$Entry getSelected();

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
        for (AbstractSelectionList$Entry entry : children()) {
            if (entry.isMouseOver(mouseX, mouseY)) {
                setSelected(entry);
                return true;
            }
        }
        return false;
    }
}

