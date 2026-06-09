package dev.gxlg.librgetter.gui.goals.select;

import dev.gxlg.librgetter.gui.GuiConstants;
import dev.gxlg.librgetter.gui.goals.AbstractDynamicWidgetScreen;
import dev.gxlg.librgetter.gui.goals.add.AddCustomGoalScreen;
import dev.gxlg.librgetter.gui.goals.add.AddGoalScreen;
import dev.gxlg.librgetter.gui.widgets.WidgetDimensions;
import dev.gxlg.librgetter.gui.widgets.unified.UnifiedWidget;
import dev.gxlg.librgetter.gui.widgets.unified.editbox.VEditBox;
import dev.gxlg.librgetter.savefiles.goals.GoalListManager;
import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.api.V;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.screens.Screen;

public class SelectEnchantmentScreen extends AbstractDynamicWidgetScreen {
    public static final R.RClass clazz = R.extendWrapper(AbstractDynamicWidgetScreen.class, SelectEnchantmentScreen.class);

    private final GoalListManager goalListManager;

    private final Screen lastScreen;

    private UnifiedEnchantmentSelectionList selectionList = null;

    public SelectEnchantmentScreen(Screen lastScreen, GoalListManager goalListManager) {
        super(Texts.literal("Select Enchantment Screen"));
        this.goalListManager = goalListManager;
        this.lastScreen = lastScreen;
    }

    @Override
    protected void initWidgets() {
        VEditBox searchBox = (VEditBox) addDynamicWidget(
            (x, y, w, h) -> new VEditBox(getFontField(), x, y, w, h, Texts.literal("")),
            (w, h) -> WidgetDimensions.from(w / 2 - GuiConstants.BUTTON_WIDTH, GuiConstants.PADDING, GuiConstants.BUTTON_WIDTH * 2, GuiConstants.BUTTON_HEIGHT)
        );
        searchBox.setResponder(this::onSearchUpdated);
        searchBox.setHint(Texts.literal("Search enchantments...."));

        selectionList = (UnifiedEnchantmentSelectionList) addDynamicWidget(
            (x, y, w, h) -> createList(y, w, h),
            (w, h) -> WidgetDimensions.from(0, GuiConstants.PADDING * 2 + GuiConstants.BUTTON_HEIGHT, w, h - GuiConstants.PADDING * 4 - GuiConstants.BUTTON_HEIGHT * 2)
        );
        addDynamicWidget((x, y, w, h) -> GuiConstants.createButton(Texts.literal("Select"), x, y, w, h, (button) -> onSelect()), GuiConstants.LEFT_BUTTON_DIMENSIONS);
        addDynamicWidget((x, y, w, h) -> GuiConstants.createButton(Texts.literal("Add custom..."), x, y, w, h, (button) -> onAddCustomPressed()), GuiConstants.RIGHT_BUTTON_DIMENSIONS);
    }

    private void onSearchUpdated(String filter) {
        if (selectionList != null) {
            selectionList.filterEntries(filter);
        }
    }

    private void onSelect() {
        if (selectionList == null) {
            return;
        }
        EnchantmentSelectionList.EnchantmentEntry selected = (EnchantmentSelectionList.EnchantmentEntry) selectionList.getSelected();
        if (selected == null) {
            return;
        }
        getMinecraftField().setScreen(new AddGoalScreen(this, lastScreen, selected.getEnchantment(), selected.getIdString(), selected.getTranslatedName(), goalListManager));
    }

    private void onAddCustomPressed() {
        getMinecraftField().setScreen(new AddCustomGoalScreen(this, lastScreen, goalListManager));
    }

    @Override
    public void onClose() {
        getMinecraftField().setScreen(lastScreen);
    }

    private UnifiedWidget createList(int y, int width, int height) {
        if (V.lower("1.20.3")) {
            return new EnchantmentSelectionList_1_20_2(this, y, width, height);
        } else {
            return new EnchantmentSelectionList(this, y, width, height);
        }
    }

}
