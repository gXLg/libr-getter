package dev.gxlg.librgetter.gui.lib.widgets;

import dev.gxlg.librgetter.gui.lib.widgets.unified.UnifiedWidget;

public interface DynamicWidgetCreator {
    UnifiedWidget create(int x, int y, int width, int height);
}
