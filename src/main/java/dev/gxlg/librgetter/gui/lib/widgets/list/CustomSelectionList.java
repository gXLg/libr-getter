package dev.gxlg.librgetter.gui.lib.widgets.list;

import dev.gxlg.librgetter.gui.lib.GuiConstants;
import dev.gxlg.librgetter.gui.lib.widgets.unified.list.UnifiedListWidget;
import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.gen.net.minecraft.client.Minecraft;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.ObjectSelectionList;
import dev.gxlg.versiont.gen.net.minecraft.client.input.KeyEvent;
import dev.gxlg.versiont.gen.net.minecraft.client.input.MouseButtonEvent;
import org.jspecify.annotations.NonNull;

public class CustomSelectionList extends ObjectSelectionList implements UnifiedListWidget {
    public static final R.RClass clazz = R.extendWrapper(ObjectSelectionList.class, CustomSelectionList.class);

    private final GuiConstants.KeyPressCallback keyPressCallback;

    public CustomSelectionList(int width, int height, int y0, int y1, int itemHeight, GuiConstants.KeyPressCallback keyPressCallback) {
        super(Minecraft.getInstance(), width, height, y0, y1, itemHeight);
        this.keyPressCallback = keyPressCallback;
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
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyPressCallback != null && keyPressCallback.onKeyPress(keyCode)) {
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean keyPressed(@NonNull KeyEvent event) {
        if (keyPressCallback != null && keyPressCallback.onKeyPress(event.key())) {
            return true;
        }
        return super.keyPressed(event);
    }
}
