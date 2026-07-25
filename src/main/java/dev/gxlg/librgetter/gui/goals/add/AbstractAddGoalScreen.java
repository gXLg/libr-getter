package dev.gxlg.librgetter.gui.goals.add;

import dev.gxlg.librgetter.gui.GuiConstants;
import dev.gxlg.librgetter.gui.goals.AbstractDynamicWidgetScreen;
import dev.gxlg.librgetter.gui.widgets.WidgetDimensions;
import dev.gxlg.librgetter.gui.widgets.unified.UnifiedWidget;
import dev.gxlg.librgetter.gui.widgets.unified.editbox.UnifiedEditBox;
import dev.gxlg.librgetter.savefiles.goals.GoalListManager;
import dev.gxlg.librgetter.utils.chaining.gui.Gui;
import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.messages.translatable.partial.TranslatablePartialMessage;
import dev.gxlg.librgetter.utils.messages.translatable.partial.gui.TranslatableAddButton;
import dev.gxlg.librgetter.utils.messages.translatable.partial.gui.TranslatableEnchantmentLabel;
import dev.gxlg.librgetter.utils.messages.translatable.partial.gui.TranslatableLevelLabel;
import dev.gxlg.librgetter.utils.messages.translatable.partial.gui.TranslatablePriceLabel;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.Font;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.screens.Screen;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Component;
import dev.gxlg.versiont.gen.net.minecraft.resources.Identifier;
import dev.gxlg.versiont.gen.net.minecraft.world.item.Items;

import java.util.function.Supplier;

public abstract class AbstractAddGoalScreen extends AbstractDynamicWidgetScreen {
    public static final R.RClass clazz = R.extendWrapper(AbstractDynamicWidgetScreen.class, AbstractAddGoalScreen.class);

    public static final TranslatablePartialMessage ENCHANTMENT_LABEL = new TranslatableEnchantmentLabel();

    public static final TranslatablePartialMessage LEVEL_LABEL = new TranslatableLevelLabel();

    public static final TranslatablePartialMessage PRICE_LABEL = new TranslatablePriceLabel();

    public static final TranslatablePartialMessage ADD_BUTTON = new TranslatableAddButton();

    private final Screen lastScreen;

    private final Screen previousScreen;

    private final GoalListManager goalListManager;

    private UnifiedEditBox levelInput = null;

    private UnifiedEditBox priceInput = null;

    protected AbstractAddGoalScreen(Component title, Screen lastScreen, Screen previousScreen, GoalListManager goalListManager) {
        super(title);
        this.lastScreen = lastScreen;
        this.previousScreen = previousScreen;
        this.goalListManager = goalListManager;
    }

    @Override
    protected void initWidgets() {
        Font font = getFontField();

        Component enchantmentLabel = ENCHANTMENT_LABEL.getComponent();
        Component levelLabel = LEVEL_LABEL.getComponent();
        Component priceLabel = PRICE_LABEL.getComponent();
        Component addButton = ADD_BUTTON.getComponent();

        int labelWidth = Math.max(Math.max(font.width(enchantmentLabel), font.width(levelLabel)), font.width(priceLabel)) * 2;

        addDynamicWidget((x, y, w, h) -> GuiConstants.createStringWidget(enchantmentLabel, x, y, w, h, font), (w, h) -> getDimensions(w, h, labelWidth, 0, 0));
        addDynamicWidget((x, y, w, h) -> GuiConstants.createStringWidget(levelLabel, x, y, w, h, font), (w, h) -> getDimensions(w, h, labelWidth, 0, 1));
        addDynamicWidget((x, y, w, h) -> GuiConstants.createStringWidget(priceLabel, x, y, w, h, font), (w, h) -> getDimensions(w, h, labelWidth, 0, 2));

        addDynamicWidget(this::createEnchantmentWidget, (w, h) -> getDimensions(w, h, labelWidth, 1, 0));

        levelInput = (UnifiedEditBox) addDynamicWidget((x, y, w, h) -> GuiConstants.createEditBox(font, x, y, w, h, Texts.literal("")), (w, h) -> getDimensions(w, h, labelWidth, 1, 1));
        int maxLevel = getMaxLevel();
        if (maxLevel != Integer.MIN_VALUE) {
            levelInput.setHint(Texts.literal(String.valueOf(maxLevel)));
        }

        priceInput = (UnifiedEditBox) addDynamicWidget((x, y, w, h) -> GuiConstants.createEditBox(font, x, y, w, h, Texts.literal("")), (w, h) -> getDimensions(w, h, labelWidth, 1, 2));
        priceInput.setHint(Texts.literal(String.valueOf(Items.EMERALD().getDefaultMaxStackSize())));

        addDynamicWidget(
            (x, y, w, h) -> GuiConstants.createButton(addButton, x, y, w, h, b -> onAddGoal(), Supplier::get), (w, h) -> {
                int y = h / 2 + GuiConstants.PADDING / 2 + GuiConstants.BUTTON_HEIGHT + GuiConstants.PADDING;
                return WidgetDimensions.from(w / 2 - labelWidth, y, labelWidth * 2, GuiConstants.BUTTON_HEIGHT);
            }
        );
    }

    private WidgetDimensions getDimensions(int width, int height, int labelWidth, int offsetX, int offsetY) {
        int innerX = width / 2 - GuiConstants.PADDING / 2 - labelWidth;
        int innerY = height / 2 - GuiConstants.PADDING / 2 - GuiConstants.BUTTON_HEIGHT * 2 - GuiConstants.PADDING;
        return WidgetDimensions.from(
            innerX + offsetX * (labelWidth + GuiConstants.PADDING),
            innerY + offsetY * (GuiConstants.BUTTON_HEIGHT + GuiConstants.PADDING),
            labelWidth,
            GuiConstants.BUTTON_HEIGHT
        );
    }

    protected abstract UnifiedWidget createEnchantmentWidget(int x, int y, int width, int height);

    protected int getMaxLevel() {
        return Integer.MIN_VALUE;
    }

    @Override
    public void onClose() {
        Gui.setScreen(getMinecraftField(), lastScreen);
    }

    protected abstract String getEnchantmentString();

    protected void onAddGoal() {
        String enchantmentStr = getEnchantmentString();
        if (enchantmentStr == null || enchantmentStr.isEmpty() || Identifier.tryParse(enchantmentStr) == null) {
            return;
        }

        if (levelInput == null || priceInput == null) {
            return;
        }

        String levelStr = levelInput.getValue();
        int level;
        if (levelStr.isEmpty()) {
            int maxLevel = getMaxLevel();
            if (maxLevel == Integer.MIN_VALUE) {
                return;
            }
            level = maxLevel;

        } else {
            try {
                level = Integer.parseInt(levelStr);
            } catch (NumberFormatException ignored) {
                return;
            }
        }

        String priceStr = priceInput.getValue();
        int price;
        if (priceStr.isEmpty()) {
            price = Items.EMERALD().getDefaultMaxStackSize();
        } else {
            try {
                price = Integer.parseInt(priceStr);
            } catch (NumberFormatException ignored) {
                return;
            }
        }

        if (level < 1 || price < 1) {
            return;
        }

        EnchantmentTrade addedGoal = new EnchantmentTrade(enchantmentStr, level, price);
        goalListManager.removeMatchingGoal(addedGoal);
        goalListManager.addGoal(addedGoal);
        Gui.setScreen(getMinecraftField(), previousScreen);
    }
}
