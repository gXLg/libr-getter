package dev.gxlg.librgetter.gui.widgets;

import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.AbstractWidget;

import java.util.function.Consumer;

public record DynamicWidget(AbstractWidget widget, DynamicDimensionGetter dimensions, Consumer<AbstractWidget> updater) { }
