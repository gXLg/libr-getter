package dev.gxlg.librgetter.gui.goals.list;

import dev.gxlg.librgetter.gui.widgets.list.CustomSelectionListInterface;
import dev.gxlg.librgetter.gui.widgets.unified.list.UnifiedList;
import dev.gxlg.librgetter.utils.chaining.gui.Gui;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.Font;

public interface UnifiedGoalSelectionList extends CustomSelectionListInterface, UnifiedList {
    GoalListScreen getGoalListScreen();

    default void removeSelectedGoal() {
        GoalListEntry selectedEntry = (GoalListEntry) getSelected();
        if (selectedEntry == null) {
            return;
        }
        if (getCustomEntries().size() > 1) {
            int index = getCustomEntries().indexOf(selectedEntry);
            GoalListEntry nextEntry = (GoalListEntry) getCustomEntries().get(index == getCustomEntries().size() - 1 ? index - 1 : index + 1);
            setSelected(nextEntry);
        }
        getGoalListScreen().getGoalListManager().removeGoal(selectedEntry.getTrade());
        getCustomEntries().remove(selectedEntry);
        Gui.removeListEntry(this, selectedEntry);
        Gui.refreshScrollAmount(this);
    }

    default void updateList() {
        clearEntries();
        getCustomEntries().clear();
        Font font = getGoalListScreen().getFontField();
        for (EnchantmentTrade trade : getGoalListScreen().getGoalListManager().getGoals()) {
            GoalListEntry entry = new GoalListEntry(font, trade);
            addEntry(entry);
            getCustomEntries().add(entry);
        }
    }
}
