package dev.gxlg.librgetter.gui;

import dev.gxlg.librgetter.gui.widgets.DynamicDimensionGetter;
import dev.gxlg.librgetter.gui.widgets.WidgetDimensions;
import dev.gxlg.librgetter.gui.widgets.unified.button.UnifiedButton;
import dev.gxlg.librgetter.gui.widgets.unified.button.VButton;
import dev.gxlg.librgetter.gui.widgets.unified.string.LegacyStringWidget;
import dev.gxlg.librgetter.gui.widgets.unified.string.UnifiedStringWidget;
import dev.gxlg.librgetter.gui.widgets.unified.string.VStringWidget;
import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.versiont.api.V;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.Font;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.Button$OnPressI;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Component;

import java.util.function.Supplier;

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

    public static UnifiedButton createButton(Component text, int x, int y, int width, int height, Button$OnPressI onPress) {
        if (V.lower("1.19.3")) {
            return new VButton(x, y, width, height, text, onPress.asButton$OnPress());
        } else if (V.lower("1.21.11")) {
            return new VButton(x, y, width, height, text, onPress.asButton$OnPress(), Supplier::get);
        } else {
            return new VButton.Plain(x, y, width, height, text, onPress.asButton$OnPress());
        }
    }

    public static UnifiedStringWidget createStringWidget(String string, int x, int y, int width, int height, Font font) {
        if (V.lower("1.19.4")) {
            LegacyStringWidget stringWidget = new LegacyStringWidget(x, y, string, font);
            stringWidget.setWidthField(width);
            stringWidget.setHeightField(height);
            return stringWidget;

        } else {
            VStringWidget stringWidget = new VStringWidget(Texts.literal(string), font);
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
}
