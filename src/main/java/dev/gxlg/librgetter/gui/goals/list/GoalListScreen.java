package dev.gxlg.librgetter.gui.goals.list;

import dev.gxlg.librgetter.gui.GuiConstants;
import dev.gxlg.librgetter.gui.goals.AbstractDynamicWidgetScreen;
import dev.gxlg.librgetter.gui.goals.select.SelectEnchantmentScreen;
import dev.gxlg.librgetter.gui.widgets.WidgetDimensions;
import dev.gxlg.librgetter.gui.widgets.unified.UnifiedWidget;
import dev.gxlg.librgetter.savefiles.goals.GoalListManager;
import dev.gxlg.librgetter.utils.chaining.gui.Gui;
import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.messages.translatable.partial.TranslatablePartialMessage;
import dev.gxlg.librgetter.utils.messages.translatable.partial.gui.TranslatableAddGoalButton;
import dev.gxlg.librgetter.utils.messages.translatable.partial.gui.TranslatableDoneButton;
import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.api.V;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.screens.Screen;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Component;

import java.util.function.Supplier;

public class GoalListScreen extends AbstractDynamicWidgetScreen {
    public static final R.RClass clazz = R.extendWrapper(AbstractDynamicWidgetScreen.class, GoalListScreen.class);

    public static final TranslatablePartialMessage DONE_BUTTON = new TranslatableDoneButton();

    public static final TranslatablePartialMessage ADD_GOAL_BUTTON = new TranslatableAddGoalButton();

    private final Screen lastScreen;

    private final GoalListManager goalListManager;

    public GoalListScreen(Screen lastScreen, GoalListManager goalListManager) {
        super(Texts.literal(""));
        this.lastScreen = lastScreen;
        this.goalListManager = goalListManager;
    }

    @Override
    protected void initWidgets() {
        Component addGoalButton = ADD_GOAL_BUTTON.getComponent();
        Component doneButton = DONE_BUTTON.getComponent();

        addDynamicWidget(
            (x, y, w, h) -> createList(y, w, h),
            (w, h) -> WidgetDimensions.from(0, GuiConstants.PADDING, w, h - GuiConstants.PADDING * 3 - GuiConstants.BUTTON_HEIGHT),
            l -> ((UnifiedGoalSelectionList) l).updateList()
        );

        addDynamicWidget((x, y, w, h) -> GuiConstants.createButton(addGoalButton, x, y, w, h, b -> onAddPressed(), Supplier::get), GuiConstants.LEFT_BUTTON_DIMENSIONS);
        addDynamicWidget((x, y, w, h) -> GuiConstants.createButton(doneButton, x, y, w, h, b -> onClose(), Supplier::get), GuiConstants.RIGHT_BUTTON_DIMENSIONS);
    }

    @Override
    public void onClose() {
        goalListManager.save();
        Gui.setScreen(getMinecraftField(), lastScreen);
    }

    private void onAddPressed() {
        Gui.setScreen(getMinecraftField(), new SelectEnchantmentScreen(this, goalListManager));
    }

    private UnifiedWidget createList(int y, int width, int height) {
        if (V.lower("1.20.3")) {
            return new GoalSelectionList(this, y, width, height);
        } else {
            return new GoalSelectionList_1_20_3(this, y, width, height);
        }
    }

    public GoalListManager getGoalListManager() {
        return goalListManager;
    }
}
