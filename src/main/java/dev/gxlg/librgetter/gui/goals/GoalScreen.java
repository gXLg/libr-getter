package dev.gxlg.librgetter.gui.goals;

import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.screens.Screen;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Component;

public class GoalScreen extends Screen {
    public static final R.RClass clazz = R.extendWrapper(Screen.class, GoalScreen.class);

    public GoalScreen() {
        super(TITLE);
    }

    @Override
    public boolean isInGameUi() {
        return true;
    }

    // TODO: add translation
    private static final Component TITLE = Texts.literal("LibrGetter Goals Screen");
}
