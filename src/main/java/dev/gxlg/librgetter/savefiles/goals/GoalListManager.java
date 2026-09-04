package dev.gxlg.librgetter.savefiles.goals;

import dev.gxlg.librgetter.savefiles.JsonSaveFile;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;

import java.util.List;

public class GoalListManager {
    private final JsonSaveFile<GoalListData> saveFile;

    private GoalListManager(JsonSaveFile<GoalListData> saveFile) {
        this.saveFile = saveFile;
    }

    public List<EnchantmentTrade> getGoals() {
        return List.copyOf(saveFile.accessData());
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean addGoal(EnchantmentTrade goal) {
        return saveFile.accessData().add(goal);
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean removeGoal(EnchantmentTrade goal) {
        return saveFile.accessData().remove(goal);
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean removeMatchingGoal(EnchantmentTrade trade) {
        GoalListData accessedData = saveFile.accessData();
        for (EnchantmentTrade goal : accessedData) {
            if (trade.same(goal)) {
                return accessedData.remove(goal);
            }
        }
        return false;
    }

    public void clearGoals() {
        saveFile.accessData().clear();
    }

    public void save() {
        saveFile.save();
    }

    public static GoalListManager init(JsonSaveFile<GoalListData> saveFile) {
        return new GoalListManager(saveFile);
    }
}
