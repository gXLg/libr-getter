package dev.gxlg.librgetter.savefiles.tradehalls;

import dev.gxlg.librgetter.savefiles.JsonSaveFile;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.versiont.gen.net.minecraft.core.BlockPos;

import java.util.List;

public class TradehallManager {

    private final JsonSaveFile<WorkstationList> saveFile;

    private TradehallManager(JsonSaveFile<WorkstationList> saveFile) {
        this.saveFile = saveFile;
    }

    public WorkstationList getWorkstations() {
        return new WorkstationList(saveFile.accessData());
    }

    public void removeWorkstation(BlockPos lecternPos) {
        WorkstationList.Workstation workstation = saveFile.accessData().findWorkstation(lecternPos);
        if (workstation != null) {
            saveFile.accessData().remove(workstation);
        }
    }

    public void addOrUpdateWorkstation(BlockPos lecternPos, List<EnchantmentTrade> trades) {
        saveFile.accessData().addOrUpdateWorkstation(lecternPos, trades);
    }

    public void clearWorkstations() {
        saveFile.accessData().clear();
    }

    public void save() {
        saveFile.save();
    }

    public static TradehallManager init(JsonSaveFile<WorkstationList> saveFile) {
        return new TradehallManager(saveFile);
    }
}
