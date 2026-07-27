package dev.gxlg.librgetter.gui.goals;

import dev.gxlg.librgetter.gui.widgets.DynamicDimensionGetter;
import dev.gxlg.librgetter.gui.widgets.DynamicWidget;
import dev.gxlg.librgetter.gui.widgets.DynamicWidgetCreator;
import dev.gxlg.librgetter.gui.widgets.WidgetDimensions;
import dev.gxlg.librgetter.gui.widgets.unified.UnifiedWidget;
import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.events.GuiEventListener;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.screens.Screen;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractDynamicWidgetScreen extends Screen {
    public static final R.RClass clazz = R.extendWrapper(Screen.class, AbstractDynamicWidgetScreen.class);

    private final List<DynamicWidget> widgets = new ArrayList<>();

    private boolean initialized = false;

    public AbstractDynamicWidgetScreen(Component title) {
        super(title);
    }

    protected final UnifiedWidget addDynamicWidget(DynamicWidgetCreator creator, DynamicDimensionGetter updater) {
        return addDynamicWidget(creator, updater, widget -> { });
    }

    protected final UnifiedWidget addDynamicWidget(DynamicWidgetCreator creator, DynamicDimensionGetter dimensions, Consumer<UnifiedWidget> updater) {
        WidgetDimensions wd = dimensions.getDimensions(getWidthField(), getHeightField());
        UnifiedWidget widget = creator.create(wd.x(), wd.y(), wd.width(), wd.height());
        updater.accept(widget);

        widgets.add(new DynamicWidget(widget, dimensions, updater));
        addRenderableWidget((GuiEventListener) widget);

        return widget;
    }

    @Override
    protected final void clearWidgets() {
    }

    @Override
    protected final void repositionElements() {
        init();
    }

    @Override
    protected final void init() {
        if (!initialized) {
            initWidgets();
            initialized = true;
        }
        updateWidgets();
    }

    protected abstract void initWidgets();

    private void updateWidgets() {
        widgets.forEach(dw -> {
            UnifiedWidget widget = dw.widget();
            WidgetDimensions wd = dw.dimensions().getDimensions(getWidthField(), getHeightField());
            widget.setXField(wd.x());
            widget.setYField(wd.y());
            widget.setWidthField(wd.width());
            widget.setHeightField(wd.height());
            dw.updater().accept(widget);
        });
    }
}
