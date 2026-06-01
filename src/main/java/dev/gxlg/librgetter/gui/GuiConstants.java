package dev.gxlg.librgetter.gui;

import dev.gxlg.librgetter.gui.widgets.DynamicDimensionGetter;
import dev.gxlg.librgetter.gui.widgets.WidgetDimensions;
import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.versiont.api.V;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.Font;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.AbstractWidget;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.Button;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.Button$OnPressI;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.StringWidget;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Component;

public class GuiConstants {
    public static final int PADDING = 12;

    public static final int BUTTON_WIDTH = 100;

    public static final int BUTTON_HEIGHT = 18;

    public static final DynamicDimensionGetter LEFT_BUTTON_DIMENSIONS = (w, h) -> WidgetDimensions.from(
        w / 2 - GuiConstants.PADDING / 2 - GuiConstants.BUTTON_WIDTH,
        h - GuiConstants.PADDING - GuiConstants.BUTTON_HEIGHT,
        GuiConstants.BUTTON_WIDTH,
        GuiConstants.BUTTON_HEIGHT
    );

    public static final DynamicDimensionGetter RIGHT_BUTTON_DIMENSIONS = (w, h) -> WidgetDimensions.from(
        w / 2 + GuiConstants.PADDING / 2,
        h - GuiConstants.PADDING - GuiConstants.BUTTON_HEIGHT,
        GuiConstants.BUTTON_WIDTH,
        GuiConstants.BUTTON_HEIGHT
    );

    public static Button createButton(Component text, int x, int y, int width, int height, Button$OnPressI onPress) {
        // TODO: in older versions - button constructor
        return Button.builder(text, onPress.asButton$OnPress()).pos(x, y).size(width, height).build();
    }

    public static AbstractWidget createStringWidget(String string, int x, int y, int width, int height, Font font) {
        // TODO: in older versions - use LegacyStringWidget
        StringWidget stringWidget = new StringWidget(Texts.literal(string), font);
        stringWidget.setWidthField(width);
        stringWidget.setHeightField(height);
        stringWidget.setXField(x);
        stringWidget.setYField(y);
        if (V.lower("1.21.9")) {
            stringWidget.alignLeft();
        }
        return stringWidget;
    }
}
