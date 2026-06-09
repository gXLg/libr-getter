package dev.gxlg.librgetter.gui.widgets;

import dev.gxlg.librgetter.gui.widgets.unified.UnifiedWidget;

public interface DynamicWidgetCreator {
    UnifiedWidget create(int x, int y, int width, int height);
}
