package dev.gxlg.librgetter.gui.goals;

import dev.gxlg.librgetter.gui.GuiConstants;
import dev.gxlg.librgetter.gui.widgets.WidgetDimensions;
import dev.gxlg.librgetter.savefiles.goals.GoalListManager;
import dev.gxlg.librgetter.utils.chaining.enchantments.Enchantments;
import dev.gxlg.librgetter.utils.chaining.gui.Gui;
import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.exceptions.common.InternalErrorException;
import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.gen.net.minecraft.client.Minecraft;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.Font;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.GuiGraphicsExtractor;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.AbstractSelectionList$Entry;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.EditBox;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.ObjectSelectionList;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.ObjectSelectionList$Entry;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.ObjectSelectionList$EntryI;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.screens.Screen;
import dev.gxlg.versiont.gen.net.minecraft.client.multiplayer.ClientLevel;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Component;
import dev.gxlg.versiont.gen.net.minecraft.resources.Identifier;
import dev.gxlg.versiont.gen.net.minecraft.world.item.enchantment.Enchantment;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public class SelectEnchantmentScreen extends AbstractDynamicWidgetScreen {
    public static final R.RClass clazz = R.extendWrapper(AbstractDynamicWidgetScreen.class, SelectEnchantmentScreen.class);

    private final GoalListManager goalListManager;

    private final Screen lastScreen;

    private SelectionList selectionList = null;

    public SelectEnchantmentScreen(Screen lastScreen, GoalListManager goalListManager) {
        super(Texts.literal("Select Enchantment Screen"));
        this.goalListManager = goalListManager;
        this.lastScreen = lastScreen;
    }

    @Override
    protected void initWidgets() {
        EditBox searchBox = (EditBox) addDynamicWidget(
            (x, y, w, h) -> new EditBox(getFontField(), x, y, w, h, Texts.literal("")),
            (w, h) -> WidgetDimensions.from(w / 2 - GuiConstants.BUTTON_WIDTH, GuiConstants.PADDING, GuiConstants.BUTTON_WIDTH * 2, GuiConstants.BUTTON_HEIGHT)
        );
        searchBox.setResponder(this::onSearchUpdated);
        searchBox.setHint(Texts.literal("Search enchantments...."));

        selectionList = (SelectionList) addDynamicWidget(
            (x, y, w, h) -> new SelectionList(y, w, h),
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
        SelectionList.EnchantmentEntry selected = (SelectionList.EnchantmentEntry) selectionList.getSelected();
        if (selected == null) {
            return;
        }
        getMinecraftField().setScreen(new AddGoalScreen(this, lastScreen, selected.enchantment, selected.idString, selected.translatedName, goalListManager));
    }

    private void onAddCustomPressed() {
        getMinecraftField().setScreen(new AddCustomGoalScreen(this, lastScreen, goalListManager));
    }

    @Override
    public void onClose() {
        getMinecraftField().setScreen(lastScreen);
    }

    public class SelectionList extends ObjectSelectionList {
        public static final R.RClass clazz = R.extendWrapper(ObjectSelectionList.class, SelectionList.class);

        private final List<EnchantmentEntry> entries = new ArrayList<>();

        public SelectionList(int y, int w, int h) {
            super(SelectEnchantmentScreen.this.getMinecraftField(), w, h, y, 18);

            ClientLevel level = Minecraft.getInstance().getLevelField();
            if (level == null) {
                return;
            }

            for (Enchantment enchantment : Enchantments.getAllEnchantments()) {
                EnchantmentEntry entry = new EnchantmentEntry(enchantment);
                addEntry(entry);
                entries.add(entry);
            }
        }

        public int getRowWidth() {
            return super.getRowWidth() + 50;
        }

        private void filterEntries(final String filter) {
            List<AbstractSelectionList$Entry> filtered = entries.stream().filter(e -> filter.isEmpty() || e.translatedName.contains(filter) || e.idString.contains(filter))
                                                                .map(e -> (AbstractSelectionList$Entry) e).toList();
            replaceEntries(filtered);
            Gui.refreshScrollAmount(this);
        }

        public class EnchantmentEntry extends ObjectSelectionList$Entry implements ObjectSelectionList$EntryI {
            public static final R.RClass clazz = R.extendWrapper(ObjectSelectionList$Entry.class, EnchantmentEntry.class);

            private final Enchantment enchantment;

            private final String idString;

            private final String translatedName;

            private final boolean tradeable;

            public EnchantmentEntry(Enchantment enchantment) {
                this.enchantment = enchantment;
                Identifier id = Enchantments.enchantmentId(enchantment);
                this.idString = id.toString();
                this.translatedName = Texts.translateIdentifier(Texts.IdentifierType.ENCHANTMENT, id);
                boolean tradeable;
                try {
                    tradeable = Enchantments.canBeTraded(enchantment);
                } catch (InternalErrorException ignored) {
                    tradeable = true;
                }
                this.tradeable = tradeable;
            }

            @Override
            public @NonNull Component getNarration() {
                return Texts.literal(translatedName);
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

                Gui.extractText(graphics, font, translatedName, contentX, contentY, -1);
                if (!tradeable) {
                    String text = "(not tradeable)";
                    Gui.extractText(graphics, getFontField(), text, contentX + contentWidth - font.width(text), contentY, -1);
                }
            }
        }
    }

}
