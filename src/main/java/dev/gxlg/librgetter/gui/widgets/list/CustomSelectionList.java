package dev.gxlg.librgetter.gui.widgets.list;

import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.api.V;
import dev.gxlg.versiont.gen.net.minecraft.client.Minecraft;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.ObjectSelectionList;
import dev.gxlg.versiont.gen.net.minecraft.client.input.MouseButtonEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class CustomSelectionList extends ObjectSelectionList implements CustomSelectionListInterface {
    public static final R.RClass clazz = R.extendWrapper(ObjectSelectionList.class, CustomSelectionList.class);

    protected final List<CustomSelectionListEntry> entries = new ArrayList<>();

    public CustomSelectionList(Minecraft minecraft, int width, int height, int y0, int y1, int itemHeight) {
        super(minecraft, width, height, y0, y1, itemHeight);
    }

    @Override
    public List<CustomSelectionListEntry> getCustomEntries() {
        return entries;
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

    @Override
    public int getXField() {
        return getX0Field();
    }

    @Override
    public int getYField() {
        return getY0Field();
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
