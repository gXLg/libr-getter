package dev.gxlg.librgetter.savefiles.goals;

import dev.gxlg.librgetter.savefiles.JsonSaveFile;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;

import java.util.List;

public class GoalListManager {
    private final JsonSaveFile<GoalListData> saveFile;

    private GoalListManager(JsonSaveFile<GoalListData> saveFile) {
        this.saveFile = saveFile;
    }

    private GoalListData getData() {
        return saveFile.getData();
    }

    public List<EnchantmentTrade> getGoals() {
        return List.copyOf(getData());
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean addGoal(EnchantmentTrade goal) {
        return getData().add(goal);
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean removeGoal(EnchantmentTrade goal) {
        return getData().remove(goal);
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean removeMatchingGoal(EnchantmentTrade trade) {
        for (EnchantmentTrade goal : getData()) {
            if (trade.same(goal)) {
                return getData().remove(goal);
            }
        }
        return false;
    }

    public void clearGoals() {
        getData().clear();
    }

    public void save() {
        saveFile.save();
    }

    public static GoalListManager init(JsonSaveFile<GoalListData> saveFile) {
        return new GoalListManager(saveFile);
    }
}
