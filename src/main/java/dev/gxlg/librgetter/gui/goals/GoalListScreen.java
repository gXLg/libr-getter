package dev.gxlg.librgetter.gui.goals;

import dev.gxlg.librgetter.gui.GuiConstants;
import dev.gxlg.librgetter.gui.widgets.WidgetDimensions;
import dev.gxlg.librgetter.savefiles.goals.GoalListManager;
import dev.gxlg.librgetter.utils.chaining.gui.Gui;
import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.messages.objects.trades.TradeMessage;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.Font;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.GuiGraphicsExtractor;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.ObjectSelectionList;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.ObjectSelectionList$Entry;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.ObjectSelectionList$EntryI;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.screens.Screen;
import dev.gxlg.versiont.gen.net.minecraft.client.input.KeyEvent;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

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
            (x, y, w, h) -> new SelectionList(y, w, h),
            (w, h) -> WidgetDimensions.from(0, GuiConstants.PADDING, w, h - GuiConstants.PADDING * 3 - GuiConstants.BUTTON_HEIGHT),
            l -> ((SelectionList) l).updateList()
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

    public class SelectionList extends ObjectSelectionList {
        public static final R.RClass clazz = R.extendWrapper(ObjectSelectionList.class, SelectionList.class);

        private final List<GoalEntry> entries = new ArrayList<>();

        public SelectionList(int y, int w, int h) {
            super(GoalListScreen.this.getMinecraftField(), w, h, y, 18);
        }

        @Override
        public int getRowWidth() {
            return super.getRowWidth() + 50;
        }

        private void removeSelectedGoal() {
            GoalEntry selectedEntry = (GoalEntry) getSelected();
            if (selectedEntry == null) {
                return;
            }
            if (entries.size() > 1) {
                int index = entries.indexOf(selectedEntry);
                GoalEntry nextEntry = entries.get(index == entries.size() - 1 ? index - 1 : index + 1);
                setSelected(nextEntry);
            }
            GoalListScreen.this.goalListManager.removeGoal(selectedEntry.trade);
            entries.remove(selectedEntry);
            Gui.removeListEntry(this, selectedEntry);
            refreshScrollAmount();
        }

        @Override
        public boolean keyPressed(@NonNull KeyEvent event) {
            if (event.key() == GLFW.GLFW_KEY_DELETE) {
                removeSelectedGoal();
                return true;
            }
            return super.keyPressed(event);
        }

        @Override
        public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
            if (keyCode == GLFW.GLFW_KEY_DELETE) {
                removeSelectedGoal();
                return true;
            }
            return super.keyPressed(keyCode, scanCode, modifiers);
        }

        public void updateList() {
            clearEntries();
            entries.clear();
            for (EnchantmentTrade trade : GoalListScreen.this.goalListManager.getGoals()) {
                GoalEntry entry = new GoalEntry(trade);
                this.addEntry(entry);
                entries.add(entry);
            }
        }

        public class GoalEntry extends ObjectSelectionList$Entry implements ObjectSelectionList$EntryI {
            public static final R.RClass clazz = R.extendWrapper(ObjectSelectionList$Entry.class, GoalEntry.class);

            private final EnchantmentTrade trade;

            private final Component component;

            private final String priceString;

            public GoalEntry(EnchantmentTrade trade) {
                this.trade = trade;
                this.component = new TradeMessage(trade).getComponent();
                this.priceString = String.valueOf(trade.price());
            }

            @Override
            public @NonNull Component getNarration() {
                return component;
            }

            @Override
            public void extractContent(@NonNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, boolean hovered, float a) {
                render(graphics, 0, getYField(), getXField(), getWidthField(), getHeightField(), mouseX, mouseY, hovered, a);
            }

            @Override
            public void render(GuiGraphicsExtractor graphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTicks) {
                Font font = getFontField();
                int contentX = left + 7;
                int contentY = top + height / 2 - 4;
                int contentWidth = width - 14;

                Gui.extractText(graphics, font, component, contentX, contentY, -1);
                Gui.extractText(graphics, font, priceString, contentX + contentWidth - font.width(priceString), contentY, -1);
            }
        }
    }
}
