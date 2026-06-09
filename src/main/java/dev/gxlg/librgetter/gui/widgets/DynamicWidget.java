package dev.gxlg.librgetter.gui.widgets;

import dev.gxlg.librgetter.gui.widgets.unified.UnifiedWidget;

import java.util.function.Consumer;

public record DynamicWidget(UnifiedWidget widget, DynamicDimensionGetter dimensions, Consumer<UnifiedWidget> updater) { }
