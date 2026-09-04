package dev.gxlg.librgetter.gui.impl.goals.list;

import dev.gxlg.librgetter.gui.impl.goals.AbstractDynamicWidgetScreen;
import dev.gxlg.librgetter.gui.impl.goals.select.SelectEnchantmentScreen;
import dev.gxlg.librgetter.gui.lib.GuiConstants;
import dev.gxlg.librgetter.gui.lib.widgets.WidgetDimensions;
import dev.gxlg.librgetter.gui.lib.widgets.unified.list.UnifiedListWidget;
import dev.gxlg.librgetter.savefiles.goals.GoalListAccessor;
import dev.gxlg.librgetter.savefiles.goals.GoalListManager;
import dev.gxlg.librgetter.utils.chaining.gui.Gui;
import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.messages.translatable.partial.TranslatablePartialMessage;
import dev.gxlg.librgetter.utils.messages.translatable.partial.gui.TranslatableAddGoalButton;
import dev.gxlg.librgetter.utils.messages.translatable.partial.gui.TranslatableClearButton;
import dev.gxlg.librgetter.utils.messages.translatable.partial.gui.TranslatableDoneButton;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.screens.Screen;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class GoalListScreen extends AbstractDynamicWidgetScreen {
    public static final R.RClass clazz = R.extendWrapper(AbstractDynamicWidgetScreen.class, GoalListScreen.class);

    public static final TranslatablePartialMessage DONE_BUTTON = new TranslatableDoneButton();

    public static final TranslatablePartialMessage ADD_GOAL_BUTTON = new TranslatableAddGoalButton();

    public static final TranslatablePartialMessage CLEAR_BUTTON = new TranslatableClearButton();

    private final Screen lastScreen;

    private final GoalListManager goalListManager;

    private final List<GoalListEntry> customEntries = new ArrayList<>();

    private UnifiedListWidget selectionList = null;

    public GoalListScreen(Screen lastScreen, GoalListAccessor goalListAccessor) {
        super(Texts.literal(""));
        this.lastScreen = lastScreen;
        this.goalListManager = goalListAccessor.createAccessForCurrentManager();
    }

    @Override
    protected void initWidgets() {
        Component addGoalButton = ADD_GOAL_BUTTON.getComponent();
        Component doneButton = DONE_BUTTON.getComponent();
        Component clearButton = CLEAR_BUTTON.getComponent();

        selectionList = (UnifiedListWidget) addDynamicWidget(
            (x, y, w, h) -> GuiConstants.createListWidget(y, w, h, GuiConstants.BUTTON_HEIGHT, this::onKeyPress),
            GuiConstants.DEFAULT_LIST,
            u -> updateList()
        );

        addDynamicWidget((x, y, w, h) -> GuiConstants.createButton(addGoalButton, x, y, w, h, b -> onAddPressed()), GuiConstants.BOTTOM_LEFT_DIMENSIONS);
        addDynamicWidget((x, y, w, h) -> GuiConstants.createButton(doneButton, x, y, w, h, b -> onClose()), GuiConstants.BOTTOM_RIGHT_DIMENSIONS);
        addDynamicWidget(
            (x, y, w, h) -> GuiConstants.createButton(clearButton, x, y, w, h, b -> onClearButtonPressed()),
            (w, h) -> WidgetDimensions.from(w - GuiConstants.PADDING - GuiConstants.BUTTON_WIDTH / 2, GuiConstants.PADDING, GuiConstants.BUTTON_WIDTH / 2, GuiConstants.BUTTON_HEIGHT)
        );
    }

    private void updateList() {
        if (selectionList == null) {
            return;
        }
        selectionList.clearEntries();
        customEntries.clear();
        for (EnchantmentTrade trade : goalListManager.getGoals()) {
            GoalListEntry entry = new GoalListEntry(getFontField(), trade);
            selectionList.addEntry(entry);
            customEntries.add(entry);
        }
    }

    private boolean onKeyPress(int keyCode) {
        if (keyCode == GLFW.GLFW_KEY_DELETE) {
            return removeSelectedGoal();
        }
        return false;
    }

    private boolean removeSelectedGoal() {
        if (selectionList == null) {
            return false;
        }
        GoalListEntry selectedEntry = (GoalListEntry) selectionList.getSelected();
        if (selectedEntry == null) {
            return false;
        }
        if (customEntries.size() > 1) {
            int index = customEntries.indexOf(selectedEntry);
            GoalListEntry nextEntry = customEntries.get(index == customEntries.size() - 1 ? index - 1 : index + 1);
            selectionList.setSelected(nextEntry);
        }
        goalListManager.removeGoal(selectedEntry.getTrade());
        customEntries.remove(selectedEntry);
        Gui.removeListEntry(selectionList, selectedEntry);
        Gui.refreshScrollAmount(selectionList);
        return true;
    }

    private void onClearButtonPressed() {
        goalListManager.clearGoals();
        updateList();
    }

    @Override
    public void onClose() {
        goalListManager.save();
        Gui.setScreen(getMinecraftField(), lastScreen);
    }

    private void onAddPressed() {
        Gui.setScreen(getMinecraftField(), new SelectEnchantmentScreen(this, goalListManager));
    }
}
