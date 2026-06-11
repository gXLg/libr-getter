package dev.gxlg.librgetter.gui.widgets.unified.button;

import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.Button;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.Button$CreateNarrationI;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.Button$OnPress;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.Button$Plain;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Component;

import java.util.function.Supplier;

public class VButton extends Button implements UnifiedButton {
    public static final R.RClass clazz = R.extendWrapper(Button.class, VButton.class);

    public VButton(int x, int y, int width, int height, Component message, Button$OnPress onPress, Button$CreateNarrationI createNarration) {
        super(x, y, width, height, message, onPress, createNarration.asButton$CreateNarration());
    }

    public VButton(int x, int y, int width, int height, Component message, Button$OnPress onPress) {
        super(x, y, width, height, message, onPress);
    }

    public static class Plain extends Button$Plain implements UnifiedButton {
        public static final R.RClass clazz = R.extendWrapper(Button$Plain.class, Plain.class);

        public Plain(int x, int y, int width, int height, Component message, Button$OnPress onPress) {
            super(x, y, width, height, message, onPress, ((Button$CreateNarrationI) Supplier::get).asButton$CreateNarration());
        }
    }
}
