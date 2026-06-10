package dev.gxlg.librgetter.gui.widgets;

public record WidgetDimensions(int x, int y, int width, int height) {
    public static WidgetDimensions from(int x, int y, int width, int height) {
        return new WidgetDimensions(x, y, width, height);
    }
}
