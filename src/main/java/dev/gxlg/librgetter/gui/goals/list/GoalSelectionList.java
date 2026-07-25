package dev.gxlg.librgetter.gui.goals.list;

import dev.gxlg.librgetter.gui.widgets.list.CustomSelectionList;
import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.gen.net.minecraft.client.input.KeyEvent;
import org.jspecify.annotations.NonNull;
import org.lwjgl.glfw.GLFW;

public class GoalSelectionList extends CustomSelectionList implements UnifiedGoalSelectionList {
    public static final R.RClass clazz = R.extendWrapper(CustomSelectionList.class, GoalSelectionList.class);

    private final GoalListScreen goalListScreen;

    public GoalSelectionList(GoalListScreen goalListScreen, int y, int w, int h) {
        super(goalListScreen.getMinecraftField(), w, h, y, y + h, 18);
        this.goalListScreen = goalListScreen;
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

    @Override
    public GoalListScreen getGoalListScreen() {
        return goalListScreen;
    }
}
