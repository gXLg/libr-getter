package dev.gxlg.librgetter.savefiles.goals;

import dev.gxlg.librgetter.savefiles.JsonSaveFile;
import dev.gxlg.librgetter.savefiles.SaveFileManager;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;

import java.util.List;

public class GoalListManager {
    public static final String FILENAME = "goals.json";

    private final JsonSaveFile<GoalListData> saveFile;

    private final GoalListData data;

    private GoalListManager(JsonSaveFile<GoalListData> saveFile) {
        this.saveFile = saveFile;
        this.data = saveFile.getData();
    }

    public List<EnchantmentTrade> getGoals() {
        return List.copyOf(data.goals);
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean addGoal(EnchantmentTrade goal) {
        return data.goals.add(goal);
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean removeGoal(EnchantmentTrade goal) {
        return data.goals.remove(goal);
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean removeMatchingGoal(EnchantmentTrade trade) {
        for (EnchantmentTrade goal : data.goals) {
            if (trade.same(goal)) {
                return data.goals.remove(goal);
            }
        }
        return false;
    }

    public void clearGoals() {
        data.goals.clear();
    }

    public void save() {
        saveFile.save();
    }

    public static GoalListManager init(SaveFileManager saveFileManager) {
        JsonSaveFile<GoalListData> saveFile = saveFileManager.createSaveFile(FILENAME, GoalListData.class, GoalListData::new);
        return new GoalListManager(saveFile);
    }
}
