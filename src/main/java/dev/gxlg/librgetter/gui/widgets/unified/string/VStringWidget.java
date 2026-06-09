package dev.gxlg.librgetter.gui.widgets.unified.string;

import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.Font;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.StringWidget;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Component;

public class VStringWidget extends StringWidget implements UnifiedStringWidget {
    public static final R.RClass clazz = R.extendWrapper(StringWidget.class, VStringWidget.class);

    public VStringWidget(Component message, Font font) {
        super(message, font);
    }
}
