package dev.gxlg.librgetter.savefiles.goals;

import dev.gxlg.librgetter.notifier.Notifier;
import dev.gxlg.librgetter.savefiles.JsonSaveFile;
import dev.gxlg.librgetter.savefiles.SaveFilePathManager;
import dev.gxlg.librgetter.savefiles.access.WorldManagerAccessor;

public class GoalListAccessor extends WorldManagerAccessor<GoalListData, GoalListManager> {
    public static final String FILENAME = "goals.json";

    public GoalListAccessor(SaveFilePathManager saveFilePathManager, Notifier notifier) {
        super(saveFilePathManager, notifier, FILENAME, GoalListData.class, GoalListData::new);
    }

    @Override
    protected GoalListManager createManagerForSaveFile(JsonSaveFile<GoalListData> saveFile) {
        return GoalListManager.init(saveFile);
    }
}
