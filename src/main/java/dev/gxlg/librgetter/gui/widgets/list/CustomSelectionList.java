package dev.gxlg.librgetter.gui.widgets.list;

import dev.gxlg.librgetter.gui.widgets.unified.list.UnifiedWidgetList;
import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.api.V;
import dev.gxlg.versiont.gen.net.minecraft.client.Minecraft;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.ObjectSelectionList;
import dev.gxlg.versiont.gen.net.minecraft.client.input.MouseButtonEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class CustomSelectionList extends ObjectSelectionList implements UnifiedWidgetList {
    public static final R.RClass clazz = R.extendWrapper(ObjectSelectionList.class, CustomSelectionList.class);

    protected final List<CustomSelectionListEntry> entries = new ArrayList<>();

    public CustomSelectionList(Minecraft minecraft, int width, int height, int y0, int y1, int itemHeight) {
        super(minecraft, width, height, y0, y1, itemHeight);
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean isDoubleClick) {
        if (clickElement(event.x(), event.y(), event.button())) {
            return true;
        }
        return super.mouseClicked(event, isDoubleClick);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (clickElement(mouseX, mouseY, button)) {
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private boolean clickElement(double mouseX, double mouseY, int button) {
        if (button != 0) {
            return false;
        }
        int rowHalf = this.getRowWidth() / 2;
        int center = this.getX0Field() + this.getWidthField() / 2;
        if (mouseX < center - rowHalf || mouseX > center + rowHalf) {
            return false;
        }
        int top = getY0Field();
        int bottom = top + getHeightField();
        if (mouseY < top || mouseY > bottom) {
            return false;
        }
        for (CustomSelectionListEntry entry : entries) {
            if (entry.isMouseOver(mouseX, mouseY)) {
                setSelected(entry);
                return true;
            }
        }
        return false;
    }

    @Override
    public int getRowWidth() {
        if (V.lower("1.20.5")) {
            // before the transparent UI, the DirtUI was more clamped
            return super.getRowWidth();
        }
        return super.getRowWidth() + 50;
    }
}
