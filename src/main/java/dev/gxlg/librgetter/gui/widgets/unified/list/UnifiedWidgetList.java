package dev.gxlg.librgetter.gui.widgets.unified.list;

import dev.gxlg.librgetter.gui.widgets.unified.UnifiedWidget;

public interface UnifiedWidgetList extends UnifiedWidget {

    void setX0Field(int x0);

    void setY0Field(int y0);

    @Override
    default void setXField(int x) {
        setX0Field(x);
    }

    @Override
    default void setYField(int y) {
        setY0Field(y);
    }
}
