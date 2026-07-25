package dev.gxlg.librgetter.gui.widgets.list;

import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.api.V;
import dev.gxlg.versiont.gen.net.minecraft.client.Minecraft;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.ObjectSelectionList_1_20_3;
import dev.gxlg.versiont.gen.net.minecraft.client.input.MouseButtonEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class CustomSelectionList_1_20_3 extends ObjectSelectionList_1_20_3 implements CustomSelectionListInterface {
    public static final R.RClass clazz = R.extendWrapper(ObjectSelectionList_1_20_3.class, CustomSelectionList_1_20_3.class);

    protected final List<CustomSelectionListEntry> entries = new ArrayList<>();

    public CustomSelectionList_1_20_3(Minecraft minecraft, int width, int height, int y, int itemHeight) {
        super(minecraft, width, height, y, itemHeight);
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
    public void setX0Field(int x0) {
        super.setXField(x0);
    }

    @Override
    public void setY0Field(int y0) {
        super.setYField(y0);
    }

    @Override
    public List<CustomSelectionListEntry> getCustomEntries() {
        return entries;
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
