package dev.gxlg.librgetter.gui.goals.list;

import dev.gxlg.librgetter.gui.GuiConstants;
import dev.gxlg.librgetter.gui.goals.AbstractDynamicWidgetScreen;
import dev.gxlg.librgetter.gui.goals.select.SelectEnchantmentScreen;
import dev.gxlg.librgetter.gui.widgets.WidgetDimensions;
import dev.gxlg.librgetter.gui.widgets.unified.list.UnifiedListWidget;
import dev.gxlg.librgetter.savefiles.goals.GoalListManager;
import dev.gxlg.librgetter.utils.chaining.gui.Gui;
import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.messages.translatable.partial.TranslatablePartialMessage;
import dev.gxlg.librgetter.utils.messages.translatable.partial.gui.TranslatableAddGoalButton;
import dev.gxlg.librgetter.utils.messages.translatable.partial.gui.TranslatableDoneButton;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.Font;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.screens.Screen;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class GoalListScreen extends AbstractDynamicWidgetScreen {
    public static final R.RClass clazz = R.extendWrapper(AbstractDynamicWidgetScreen.class, GoalListScreen.class);

    public static final TranslatablePartialMessage DONE_BUTTON = new TranslatableDoneButton();

    public static final TranslatablePartialMessage ADD_GOAL_BUTTON = new TranslatableAddGoalButton();

    private final Screen lastScreen;

    private final GoalListManager goalListManager;

    private final List<GoalListEntry> customEntries = new ArrayList<>();

    private UnifiedListWidget selectionList = null;

    public GoalListScreen(Screen lastScreen, GoalListManager goalListManager) {
        super(Texts.literal(""));
        this.lastScreen = lastScreen;
        this.goalListManager = goalListManager;
    }

    @Override
    protected void initWidgets() {
        Component addGoalButton = ADD_GOAL_BUTTON.getComponent();
        Component doneButton = DONE_BUTTON.getComponent();

        selectionList = (UnifiedListWidget) addDynamicWidget(
            (x, y, w, h) -> GuiConstants.createListWidget(y, w, h, GuiConstants.BUTTON_HEIGHT, this::onKeyPress),
            (w, h) -> WidgetDimensions.from(0, GuiConstants.PADDING, w, h - GuiConstants.PADDING * 3 - GuiConstants.BUTTON_HEIGHT),
            l -> onUpdateList()
        );

        addDynamicWidget((x, y, w, h) -> GuiConstants.createButton(addGoalButton, x, y, w, h, b -> onAddPressed()), GuiConstants.LEFT_BUTTON_DIMENSIONS);
        addDynamicWidget((x, y, w, h) -> GuiConstants.createButton(doneButton, x, y, w, h, b -> onClose()), GuiConstants.RIGHT_BUTTON_DIMENSIONS);
    }

    private void onUpdateList() {
        if (selectionList != null) {
            selectionList.clearEntries();
            customEntries.clear();

            Font font = getFontField();
            for (EnchantmentTrade trade : goalListManager.getGoals()) {
                GoalListEntry entry = new GoalListEntry(font, trade);
                selectionList.addEntry(entry);
                customEntries.add(entry);
            }
        }
    }

    private boolean onKeyPress(int keyCode) {
        if (keyCode == GLFW.GLFW_KEY_DELETE) {
            removeSelectedGoal();
            return true;
        }
        return false;
    }

    private void removeSelectedGoal() {
        if (selectionList == null) {
            return;
        }
        GoalListEntry selectedEntry = (GoalListEntry) selectionList.getSelected();
        if (selectedEntry == null) {
            return;
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
    }

    @Override
    public void onClose() {
        goalListManager.save();
        Gui.setScreen(getMinecraftField(), lastScreen);
    }

    private void onAddPressed() {
        Gui.setScreen(getMinecraftField(), new SelectEnchantmentScreen(this, goalListManager));
    }

    public GoalListManager getGoalListManager() {
        return goalListManager;
    }
}
