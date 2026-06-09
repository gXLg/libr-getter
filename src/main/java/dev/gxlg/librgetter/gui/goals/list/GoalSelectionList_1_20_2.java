package dev.gxlg.librgetter.gui.goals.list;

import dev.gxlg.librgetter.gui.widgets.list.CustomSelectionList_1_20_2;
import dev.gxlg.librgetter.utils.chaining.gui.Gui;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.Font;
import dev.gxlg.versiont.gen.net.minecraft.client.input.KeyEvent;
import org.jspecify.annotations.NonNull;
import org.lwjgl.glfw.GLFW;

public class GoalSelectionList_1_20_2 extends CustomSelectionList_1_20_2 implements UnifiedGoalSelectionList {
    public static final R.RClass clazz = R.extendWrapper(CustomSelectionList_1_20_2.class, GoalSelectionList_1_20_2.class);

    private final GoalListScreen goalListScreen;

    public GoalSelectionList_1_20_2(GoalListScreen goalListScreen, int y, int w, int h) {
        super(goalListScreen.getMinecraftField(), w, h, y, y + h, 18);
        this.goalListScreen = goalListScreen;
    }

    private void removeSelectedGoal() {
        GoalSelectionList.GoalEntry selectedEntry = (GoalSelectionList.GoalEntry) getSelected();
        if (selectedEntry == null) {
            return;
        }
        if (entries.size() > 1) {
            int index = entries.indexOf(selectedEntry);
            GoalSelectionList.GoalEntry nextEntry = (GoalSelectionList.GoalEntry) entries.get(index == entries.size() - 1 ? index - 1 : index + 1);
            setSelected(nextEntry);
        }
        goalListScreen.getGoalListManager().removeGoal(selectedEntry.getTrade());
        entries.remove(selectedEntry);
        Gui.removeListEntry(this, selectedEntry);
        Gui.refreshScrollAmount(this);
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
    public void updateList() {
        clearEntries();
        entries.clear();
        Font font = goalListScreen.getFontField();
        for (EnchantmentTrade trade : goalListScreen.getGoalListManager().getGoals()) {
            GoalSelectionList.GoalEntry entry = new GoalSelectionList.GoalEntry(font, trade);
            this.addEntry(entry);
            entries.add(entry);
        }
    }
}
