package dev.gxlg.librgetter.gui.widgets.unified.editbox;

import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.Font;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.EditBox;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Component;

public class VEditBox extends EditBox implements UnifiedEditBox {
    public static final R.RClass clazz = R.extendWrapper(EditBox.class, VEditBox.class);

    public VEditBox(Font font, int x, int y, int width, int height, Component narration) {
        super(font, x, y, width, height, narration);
    }
}
