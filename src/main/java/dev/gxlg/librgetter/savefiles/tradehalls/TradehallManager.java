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
        return new WorkstationList(saveFile.getData());
    }

    public void removeWorkstation(BlockPos lecternPos) {
        WorkstationList.Workstation workstation = saveFile.getData().findWorkstation(lecternPos);
        if (workstation != null) {
            saveFile.getData().remove(workstation);
        }
    }

    public void addOrUpdateWorkstation(BlockPos lecternPos, List<EnchantmentTrade> trades) {
        saveFile.getData().addOrUpdateWorkstation(lecternPos, trades);
    }

    public void clearWorkstations() {
        saveFile.getData().clear();
    }

    public void save() {
        saveFile.save();
    }

    public static TradehallManager init(JsonSaveFile<WorkstationList> saveFile) {
        return new TradehallManager(saveFile);
    }
}
