package dev.gxlg.librgetter.gui.impl.tradehall;

import dev.gxlg.librgetter.gui.impl.goals.AbstractDynamicWidgetScreen;
import dev.gxlg.librgetter.gui.lib.GuiConstants;
import dev.gxlg.librgetter.gui.lib.widgets.WidgetDimensions;
import dev.gxlg.librgetter.gui.lib.widgets.unified.button.UnifiedButton;
import dev.gxlg.librgetter.gui.lib.widgets.unified.editbox.UnifiedEditBox;
import dev.gxlg.librgetter.gui.lib.widgets.unified.list.UnifiedListWidget;
import dev.gxlg.librgetter.savefiles.tradehalls.TradehallAccessor;
import dev.gxlg.librgetter.savefiles.tradehalls.TradehallManager;
import dev.gxlg.librgetter.savefiles.tradehalls.WorkstationList;
import dev.gxlg.librgetter.utils.chaining.gui.Gui;
import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.messages.translatable.partial.TranslatablePartialMessage;
import dev.gxlg.librgetter.utils.messages.translatable.partial.gui.TranslatableClearButton;
import dev.gxlg.librgetter.utils.messages.translatable.partial.gui.TranslatableCopyButton;
import dev.gxlg.librgetter.utils.messages.translatable.partial.gui.TranslatableDoneButton;
import dev.gxlg.librgetter.utils.messages.translatable.partial.gui.TranslatableSearchLabel;
import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.gen.net.minecraft.client.Minecraft;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.screens.Screen;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TradehallScreen extends AbstractDynamicWidgetScreen {
    public static final R.RClass clazz = R.extendWrapper(AbstractDynamicWidgetScreen.class, TradehallScreen.class);

    public static final TranslatablePartialMessage SEARCH_LABEL = new TranslatableSearchLabel();

    public static final TranslatablePartialMessage DONE_BUTTON = new TranslatableDoneButton();

    public static final TranslatablePartialMessage CLEAR_BUTTON = new TranslatableClearButton();

    public static final TranslatablePartialMessage COPY_BUTTON = new TranslatableCopyButton();

    private final Map<String, TradehallManager> tradehallManagers;

    private final List<String> tradehallManagerKeys;

    private final Screen lastScreen;

    private final List<TradehallEntry> customEntries = new ArrayList<>();

    private int tradehallManagerIndex;

    private UnifiedListWidget tradehallList = null;

    private UnifiedButton dimensionButton = null;

    private UnifiedEditBox searchBox = null;

    public TradehallScreen(Screen lastScreen, TradehallAccessor tradehallAccessor) {
        super(Texts.literal(""));
        this.tradehallManagers = tradehallAccessor.createAccessForAllManagers();
        this.tradehallManagerKeys = this.tradehallManagers.keySet().stream().sorted().toList();
        tradehallManagerIndex = this.tradehallManagerKeys.indexOf(tradehallAccessor.getCurrentAccessKey());

        this.lastScreen = lastScreen;
    }

    @Override
    protected void initWidgets() {
        Component searchLabel = SEARCH_LABEL.getComponent();
        Component doneButton = DONE_BUTTON.getComponent();
        Component clearButton = CLEAR_BUTTON.getComponent();
        Component copyButton = COPY_BUTTON.getComponent();
        Component dimensionLabel = Texts.literal(tradehallManagerKeys.get(tradehallManagerIndex));

        searchBox = (UnifiedEditBox) addDynamicWidget((x, y, w, h) -> GuiConstants.createEditBox(getFontField(), x, y, w, h, Texts.literal("")), GuiConstants.TOP_CENTER_DIMENSIONS);
        searchBox.setResponder(this::onSearchUpdated);
        searchBox.setHint(searchLabel);

        dimensionButton = (UnifiedButton) addDynamicWidget(
            (x, y, w, h) -> GuiConstants.createButton(dimensionLabel, x, y, w, h, button -> onDimensionChange()),
            (w, h) -> WidgetDimensions.from(w / 2 - GuiConstants.BUTTON_WIDTH, GuiConstants.PADDING * 2 + GuiConstants.BUTTON_HEIGHT, GuiConstants.BUTTON_WIDTH * 2, GuiConstants.BUTTON_HEIGHT)
        );

        tradehallList = (UnifiedListWidget) addDynamicWidget(
            (x, y, w, h) -> GuiConstants.createListWidget(y, w, h, GuiConstants.BUTTON_HEIGHT, this::onKeyPressed),
            (w, h) -> WidgetDimensions.from(0, GuiConstants.PADDING * 3 + GuiConstants.BUTTON_HEIGHT * 2, w, h - GuiConstants.PADDING * 5 - GuiConstants.BUTTON_HEIGHT * 3),
            u -> updateList()
        );

        addDynamicWidget((x, y, w, h) -> GuiConstants.createButton(doneButton, x, y, w, h, button -> onClose()), GuiConstants.BOTTOM_LEFT_DIMENSIONS);
        addDynamicWidget((x, y, w, h) -> GuiConstants.createButton(copyButton, x, y, w, h, button -> onCopy()), GuiConstants.BOTTOM_RIGHT_DIMENSIONS);

        addDynamicWidget(
            (x, y, w, h) -> GuiConstants.createButton(clearButton, x, y, w, h, b -> onClearButtonPressed()),
            (w, h) -> WidgetDimensions.from(w - GuiConstants.PADDING - GuiConstants.BUTTON_WIDTH / 2, GuiConstants.PADDING, GuiConstants.BUTTON_WIDTH / 2, GuiConstants.BUTTON_HEIGHT)
        );
    }

    private void onDimensionChange() {
        tradehallManagerIndex = (tradehallManagerIndex + 1) % tradehallManagerKeys.size();
        dimensionButton.setMessage(Texts.literal(tradehallManagerKeys.get(tradehallManagerIndex)));
        updateList();
    }

    private boolean onKeyPressed(int keyCode) {
        if (keyCode == GLFW.GLFW_KEY_DELETE) {
            return removeSelectedWorkstation();
        }
        return false;
    }

    private boolean removeSelectedWorkstation() {
        if (tradehallList == null) {
            return false;
        }
        TradehallEntry selectedEntry = (TradehallEntry) tradehallList.getSelected();
        if (selectedEntry == null) {
            return false;
        }
        if (customEntries.size() > 1) {
            int index = customEntries.indexOf(selectedEntry);
            TradehallEntry nextEntry = customEntries.get(index == customEntries.size() - 1 ? index - 1 : index + 1);
            tradehallList.setSelected(nextEntry);
        }
        String currentKey = tradehallManagerKeys.get(tradehallManagerIndex);
        TradehallManager tradehallManager = tradehallManagers.get(currentKey);
        tradehallManager.removeWorkstation(selectedEntry.getPosition().toBlockPos());
        customEntries.remove(selectedEntry);
        Gui.removeListEntry(tradehallList, selectedEntry);
        Gui.refreshScrollAmount(tradehallList);
        return true;
    }

    private void onClearButtonPressed() {
        String currentKey = tradehallManagerKeys.get(tradehallManagerIndex);
        TradehallManager tradehallManager = tradehallManagers.get(currentKey);
        tradehallManager.clearWorkstations();
        updateList();
    }

    private void updateList() {
        if (tradehallList == null || searchBox == null) {
            return;
        }
        String currentKey = tradehallManagerKeys.get(tradehallManagerIndex);
        TradehallManager tradehallManager = tradehallManagers.get(currentKey);
        WorkstationList workstationList = tradehallManager.getWorkstations();
        tradehallList.clearEntries();
        customEntries.clear();
        for (WorkstationList.Workstation workstation : workstationList) {
            TradehallEntry entry = new TradehallEntry(getFontField(), workstation.getPosition(), workstation.getTrades());
            tradehallList.addEntry(entry);
            customEntries.add(entry);
        }
        onSearchUpdated(searchBox.getValue());
    }

    private void onSearchUpdated(String filter) {
        if (tradehallList == null) {
            return;
        }
        tradehallList.clearEntries();
        for (TradehallEntry entry : customEntries) {
            if (entry.filterMatches(filter)) {
                tradehallList.addEntry(entry);
            }
        }
    }

    private void onCopy() {
        if (tradehallList == null) {
            return;
        }
        TradehallEntry selectedEntry = (TradehallEntry) tradehallList.getSelected();
        if (selectedEntry == null) {
            return;
        }
        String pos = selectedEntry.getPosition().toString();
        Minecraft.getInstance().getKeyboardHandlerField().setClipboard(pos);
    }

    @Override
    public void onClose() {
        tradehallManagers.forEach((k, m) -> m.save());
        Gui.setScreen(getMinecraftField(), lastScreen);
    }
}
