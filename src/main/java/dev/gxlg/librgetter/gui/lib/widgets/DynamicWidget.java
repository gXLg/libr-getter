package dev.gxlg.librgetter.gui.lib.widgets;

import dev.gxlg.librgetter.gui.lib.widgets.unified.UnifiedWidget;

import java.util.function.Consumer;

public record DynamicWidget(UnifiedWidget widget, DynamicDimensionGetter dimensions, Consumer<UnifiedWidget> updater) { }
