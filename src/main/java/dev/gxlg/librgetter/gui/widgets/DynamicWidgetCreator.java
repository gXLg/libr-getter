package dev.gxlg.librgetter.gui.widgets;

import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.AbstractWidget;

public interface DynamicWidgetCreator {
    AbstractWidget create(int x, int y, int width, int height);
}
