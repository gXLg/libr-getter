package dev.gxlg.librgetter.gui.goals;

import dev.gxlg.librgetter.savefiles.goals.GoalListManager;
import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.AbstractWidget;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.EditBox;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.screens.Screen;

public class AddCustomGoalScreen extends AbstractAddGoalScreen {
    public static final R.RClass clazz = R.extendWrapper(AbstractAddGoalScreen.class, AddCustomGoalScreen.class);

    private EditBox enchantmentInput = null;

    protected AddCustomGoalScreen(Screen lastScreen, Screen previousScreen, GoalListManager goalListManager) {
        super(Texts.literal("Add Custom Goal Screen"), lastScreen, previousScreen, goalListManager);
    }

    @Override
    protected AbstractWidget createEnchantmentWidget(int x, int y, int width, int height) {
        enchantmentInput = new EditBox(getFontField(), x, y, width, height, Texts.literal(""));
        return enchantmentInput;
    }

    @Override
    protected String getEnchantmentString() {
        return enchantmentInput.getValue();
    }
}
