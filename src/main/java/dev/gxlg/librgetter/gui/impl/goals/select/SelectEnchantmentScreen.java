package dev.gxlg.librgetter.gui.impl.goals.select;

import dev.gxlg.librgetter.gui.impl.goals.AbstractDynamicWidgetScreen;
import dev.gxlg.librgetter.gui.impl.goals.add.AddCustomGoalScreen;
import dev.gxlg.librgetter.gui.impl.goals.add.AddGoalScreen;
import dev.gxlg.librgetter.gui.lib.GuiConstants;
import dev.gxlg.librgetter.gui.lib.widgets.WidgetDimensions;
import dev.gxlg.librgetter.gui.lib.widgets.unified.editbox.UnifiedEditBox;
import dev.gxlg.librgetter.gui.lib.widgets.unified.list.UnifiedListWidget;
import dev.gxlg.librgetter.savefiles.goals.GoalListManager;
import dev.gxlg.librgetter.utils.chaining.enchantments.Enchantments;
import dev.gxlg.librgetter.utils.chaining.gui.Gui;
import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.messages.translatable.partial.TranslatablePartialMessage;
import dev.gxlg.librgetter.utils.messages.translatable.partial.gui.TranslatableAddCustomButton;
import dev.gxlg.librgetter.utils.messages.translatable.partial.gui.TranslatableSearchLabel;
import dev.gxlg.librgetter.utils.messages.translatable.partial.gui.TranslatableSelectButton;
import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.AbstractSelectionList$Entry;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.screens.Screen;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Component;
import dev.gxlg.versiont.gen.net.minecraft.world.item.enchantment.Enchantment;

import java.util.ArrayList;
import java.util.List;

public class SelectEnchantmentScreen extends AbstractDynamicWidgetScreen {
    public static final R.RClass clazz = R.extendWrapper(AbstractDynamicWidgetScreen.class, SelectEnchantmentScreen.class);

    public static final TranslatablePartialMessage SELECT_BUTTON = new TranslatableSelectButton();

    public static final TranslatablePartialMessage ADD_CUSTOM_BUTTON = new TranslatableAddCustomButton();

    public static final TranslatablePartialMessage SEARCH_LABEL = new TranslatableSearchLabel();

    private final GoalListManager goalListManager;

    private final Screen lastScreen;

    private final List<EnchantmentListEntry> customEntries = new ArrayList<>();

    private UnifiedListWidget selectionList = null;

    public SelectEnchantmentScreen(Screen lastScreen, GoalListManager goalListManager) {
        super(Texts.literal(""));
        this.goalListManager = goalListManager;
        this.lastScreen = lastScreen;
    }

    @Override
    protected void initWidgets() {
        Component selectButton = SELECT_BUTTON.getComponent();
        Component addCustomButton = ADD_CUSTOM_BUTTON.getComponent();
        Component searchLabel = SEARCH_LABEL.getComponent();

        selectionList = (UnifiedListWidget) addDynamicWidget(
            (x, y, w, h) -> GuiConstants.createListWidget(y, w, h, GuiConstants.BUTTON_HEIGHT, null),
            (w, h) -> WidgetDimensions.from(0, GuiConstants.PADDING * 2 + GuiConstants.BUTTON_HEIGHT, w, h - GuiConstants.PADDING * 4 - GuiConstants.BUTTON_HEIGHT * 2)
        );
        initList();

        UnifiedEditBox searchBox = (UnifiedEditBox) addDynamicWidget(
            (x, y, w, h) -> GuiConstants.createEditBox(getFontField(), x, y, w, h, Texts.literal("")),
            (w, h) -> WidgetDimensions.from(w / 2 - GuiConstants.BUTTON_WIDTH, GuiConstants.PADDING, GuiConstants.BUTTON_WIDTH * 2, GuiConstants.BUTTON_HEIGHT)
        );
        searchBox.setResponder(this::onSearchUpdated);
        searchBox.setHint(searchLabel);

        addDynamicWidget((x, y, w, h) -> GuiConstants.createButton(selectButton, x, y, w, h, (button) -> onSelect()), GuiConstants.LEFT_BUTTON_DIMENSIONS);
        addDynamicWidget((x, y, w, h) -> GuiConstants.createButton(addCustomButton, x, y, w, h, (button) -> onAddCustomPressed()), GuiConstants.RIGHT_BUTTON_DIMENSIONS);
    }

    private void initList() {
        if (selectionList != null) {
            for (Enchantment enchantment : Enchantments.getAllEnchantments()) {
                EnchantmentListEntry entry = new EnchantmentListEntry(getFontField(), enchantment);
                selectionList.addEntry(entry);
                customEntries.add(entry);
            }
        }
    }

    private void onSearchUpdated(String filter) {
        if (selectionList != null) {
            List<EnchantmentListEntry> filtered = customEntries.stream()
                                                               .filter(e -> filter.isEmpty() || e.getTranslatedName().contains(filter.toLowerCase()) || e.getIdString().contains(filter.toLowerCase()))
                                                               .toList();
            selectionList.replaceEntries(filtered.stream().map(e -> (AbstractSelectionList$Entry) e).toList());
            Gui.refreshScrollAmount(selectionList);
        }
    }

    private void onSelect() {
        if (selectionList == null) {
            return;
        }
        EnchantmentListEntry selected = (EnchantmentListEntry) selectionList.getSelected();
        if (selected == null) {
            return;
        }
        Gui.setScreen(getMinecraftField(), new AddGoalScreen(this, lastScreen, selected.getEnchantment(), selected.getIdString(), selected.getTranslatedName(), goalListManager));
    }

    private void onAddCustomPressed() {
        Gui.setScreen(getMinecraftField(), new AddCustomGoalScreen(this, lastScreen, goalListManager));
    }

    @Override
    public void onClose() {
        Gui.setScreen(getMinecraftField(), lastScreen);
    }
}
