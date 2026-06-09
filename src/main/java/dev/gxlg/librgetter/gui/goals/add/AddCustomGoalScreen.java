package dev.gxlg.librgetter.gui.goals.add;

import dev.gxlg.librgetter.gui.widgets.unified.UnifiedWidget;
import dev.gxlg.librgetter.gui.widgets.unified.editbox.VEditBox;
import dev.gxlg.librgetter.savefiles.goals.GoalListManager;
import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.screens.Screen;

public class AddCustomGoalScreen extends AbstractAddGoalScreen {
    public static final R.RClass clazz = R.extendWrapper(AbstractAddGoalScreen.class, AddCustomGoalScreen.class);

    private VEditBox enchantmentInput = null;

    public AddCustomGoalScreen(Screen lastScreen, Screen previousScreen, GoalListManager goalListManager) {
        super(Texts.literal("Add Custom Goal Screen"), lastScreen, previousScreen, goalListManager);
    }

    @Override
    protected UnifiedWidget createEnchantmentWidget(int x, int y, int width, int height) {
        enchantmentInput = new VEditBox(getFontField(), x, y, width, height, Texts.literal(""));
        return enchantmentInput;
    }

    @Override
    protected String getEnchantmentString() {
        return enchantmentInput.getValue();
    }
}
