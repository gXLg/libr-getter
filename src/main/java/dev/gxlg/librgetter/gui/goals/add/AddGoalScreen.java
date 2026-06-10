package dev.gxlg.librgetter.gui.goals.add;

import dev.gxlg.librgetter.gui.GuiConstants;
import dev.gxlg.librgetter.gui.widgets.unified.UnifiedWidget;
import dev.gxlg.librgetter.savefiles.goals.GoalListManager;
import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.screens.Screen;
import dev.gxlg.versiont.gen.net.minecraft.world.item.enchantment.Enchantment;

public class AddGoalScreen extends AbstractAddGoalScreen {
    public static final R.RClass clazz = R.extendWrapper(AbstractAddGoalScreen.class, AddGoalScreen.class);

    private final Enchantment enchantment;

    private final String idString;

    private final String translatedName;

    public AddGoalScreen(Screen lastScreen, Screen previousScreen, Enchantment enchantment, String idString, String translatedName, GoalListManager goalListManager) {
        super(Texts.literal(""), lastScreen, previousScreen, goalListManager);
        this.enchantment = enchantment;
        this.idString = idString;
        this.translatedName = translatedName;
    }

    @Override
    protected UnifiedWidget createEnchantmentWidget(int x, int y, int width, int height) {
        return GuiConstants.createStringWidget(Texts.literal(translatedName), x, y, width, height, getFontField());
    }

    @Override
    protected int getMaxLevel() {
        return enchantment.getMaxLevel();
    }

    @Override
    protected String getEnchantmentString() {
        return idString;
    }
}
