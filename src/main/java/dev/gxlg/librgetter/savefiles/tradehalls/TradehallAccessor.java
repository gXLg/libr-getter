package dev.gxlg.librgetter.savefiles.tradehalls;

import dev.gxlg.librgetter.notifier.Notifier;
import dev.gxlg.librgetter.savefiles.JsonSaveFile;
import dev.gxlg.librgetter.savefiles.SaveFilePathManager;
import dev.gxlg.librgetter.savefiles.access.DimensionManagerAccessor;

public class TradehallAccessor extends DimensionManagerAccessor<WorkstationList, TradehallManager> {
    public static final String FILENAME = "tradehalls.json";

    public TradehallAccessor(SaveFilePathManager saveFilePathManager, Notifier notifier) {
        super(saveFilePathManager, notifier, FILENAME, WorkstationList.class, WorkstationList::new);
    }

    @Override
    protected TradehallManager createManagerForSaveFile(JsonSaveFile<WorkstationList> saveFile) {
        return TradehallManager.init(saveFile);
    }
}
