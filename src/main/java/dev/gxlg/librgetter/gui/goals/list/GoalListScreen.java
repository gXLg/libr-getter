package dev.gxlg.librgetter.gui.goals.list;

import dev.gxlg.librgetter.gui.GuiConstants;
import dev.gxlg.librgetter.gui.goals.AbstractDynamicWidgetScreen;
import dev.gxlg.librgetter.gui.goals.select.SelectEnchantmentScreen;
import dev.gxlg.librgetter.gui.widgets.WidgetDimensions;
import dev.gxlg.librgetter.gui.widgets.unified.UnifiedWidget;
import dev.gxlg.librgetter.savefiles.goals.GoalListManager;
import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.api.V;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.screens.Screen;

public class GoalListScreen extends AbstractDynamicWidgetScreen {
    public static final R.RClass clazz = R.extendWrapper(AbstractDynamicWidgetScreen.class, GoalListScreen.class);

    private final Screen lastScreen;

    private final GoalListManager goalListManager;

    public GoalListScreen(Screen lastScreen, GoalListManager goalListManager) {
        super(Texts.literal("Goal List Screen"));
        this.lastScreen = lastScreen;
        this.goalListManager = goalListManager;
    }

    @Override
    protected void initWidgets() {
        addDynamicWidget(
            (x, y, w, h) -> createList(y, w, h),
            (w, h) -> WidgetDimensions.from(0, GuiConstants.PADDING, w, h - GuiConstants.PADDING * 3 - GuiConstants.BUTTON_HEIGHT),
            l -> ((UnifiedGoalSelectionList) l).updateList()
        );
        addDynamicWidget((x, y, w, h) -> GuiConstants.createButton(Texts.literal("Add new goal"), x, y, w, h, b -> onAddPressed()), GuiConstants.LEFT_BUTTON_DIMENSIONS);
        addDynamicWidget((x, y, w, h) -> GuiConstants.createButton(Texts.literal("Done"), x, y, w, h, b -> onClose()), GuiConstants.RIGHT_BUTTON_DIMENSIONS);
    }

    @Override
    public void onClose() {
        goalListManager.save();
        getMinecraftField().setScreen(lastScreen);
    }

    private void onAddPressed() {
        getMinecraftField().setScreen(new SelectEnchantmentScreen(this, goalListManager));
    }

    private UnifiedWidget createList(int y, int width, int height) {
        if (V.lower("1.20.3")) {
            return new GoalSelectionList_1_20_2(this, y, width, height);
        } else {
            return new GoalSelectionList(this, y, width, height);
        }
    }

    public GoalListManager getGoalListManager() {
        return goalListManager;
    }
}
