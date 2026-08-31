package dev.gxlg.librgetter.gui.lib.widgets.list;

import dev.gxlg.librgetter.gui.lib.GuiConstants;
import dev.gxlg.librgetter.gui.lib.widgets.unified.list.UnifiedListWidget;
import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.gen.net.minecraft.client.Minecraft;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.ObjectSelectionList_1_20_3;
import dev.gxlg.versiont.gen.net.minecraft.client.input.KeyEvent;
import dev.gxlg.versiont.gen.net.minecraft.client.input.MouseButtonEvent;
import org.jspecify.annotations.NonNull;

public class CustomSelectionList_1_20_3 extends ObjectSelectionList_1_20_3 implements UnifiedListWidget {
    public static final R.RClass clazz = R.extendWrapper(ObjectSelectionList_1_20_3.class, CustomSelectionList_1_20_3.class);

    private final GuiConstants.KeyPressCallback keyPressCallback;

    public CustomSelectionList_1_20_3(int width, int height, int y, int itemHeight, GuiConstants.KeyPressCallback keyPressCallback) {
        super(Minecraft.getInstance(), width, height, y, itemHeight);
        this.keyPressCallback = keyPressCallback;
    }

    @Override
    public void setX0Field(int x0) {
        setXField(x0);
    }

    @Override
    public void setY0Field(int y0) {
        setYField(y0);
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
