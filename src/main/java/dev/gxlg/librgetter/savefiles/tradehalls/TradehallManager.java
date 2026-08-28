package dev.gxlg.librgetter.savefiles.tradehalls;

import dev.gxlg.librgetter.savefiles.JsonSaveFile;
import dev.gxlg.librgetter.savefiles.SaveFileManager;
import dev.gxlg.librgetter.savefiles.WorldNameManager;
import dev.gxlg.librgetter.utils.exceptions.common.InternalErrorException;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.versiont.gen.net.minecraft.core.BlockPos;

import java.util.List;

public class TradehallManager {
    public static final String FILENAME = "tradehalls.json";

    private final JsonSaveFile<TradehallData> saveFile;

    private final TradehallData data;

    private final WorldNameManager worldNameManager;

    private TradehallManager(JsonSaveFile<TradehallData> saveFile, WorldNameManager worldNameManager) {
        this.saveFile = saveFile;
        this.data = saveFile.getData();
        this.worldNameManager = worldNameManager;
    }

    public TradehallData.WorkstationList getWorkstations() throws InternalErrorException {
        String saveFolder = getCurrentSaveFolder();
        String dimensionName = getCurrentDimension();
        return data.getWorkstations(saveFolder, dimensionName);
    }

    public void removeWorkstation(BlockPos lecternPos) throws InternalErrorException {
        String saveFolder = getCurrentSaveFolder();
        String dimension = getCurrentDimension();
        data.removeWorkstation(saveFolder, dimension, lecternPos);
    }

    public void addOrUpdateWorkstation(BlockPos lecternPos, List<EnchantmentTrade> trades) throws InternalErrorException {
        String saveFolder = getCurrentSaveFolder();
        String dimension = getCurrentDimension();
        data.addOrUpdateWorkstation(saveFolder, dimension, lecternPos, trades);
    }


    public List<String> getSaveFolders() {
        return data.getSaveFolders().stream().sorted().toList();
    }

    public String getCurrentSaveFolder() throws InternalErrorException {
        String saveFolder = worldNameManager.getSaveFolder();
        if (saveFolder == null) {
            throw new InternalErrorException("saveFolder");
        }
        return saveFolder;
    }

    public List<String> getCurrentDimensions() throws InternalErrorException {
        String saveFolder = getCurrentSaveFolder();
        return data.getDimensions(saveFolder).stream().sorted().toList();
    }

    public String getCurrentDimension() throws InternalErrorException {
        String dimensionName = worldNameManager.getDimensionName();
        if (dimensionName == null) {
            throw new InternalErrorException("dimensionName");
        }
        return dimensionName;
    }

    public void clearCurrentDimension() throws InternalErrorException {
        String saveFolder = getCurrentSaveFolder();
        String dimensionName = getCurrentDimension();
        data.clearDimension(saveFolder, dimensionName);
    }

    public void clearCurrentSaveFolder() throws InternalErrorException {
        String saveFolder = getCurrentSaveFolder();
        data.clearSaveFolder(saveFolder);
    }

    public void save() {
        this.saveFile.save();
    }

    public static TradehallManager init(SaveFileManager saveFileManager, WorldNameManager worldNameManager) {
        JsonSaveFile<TradehallData> saveFile = saveFileManager.createSaveFile(FILENAME, TradehallData.class, TradehallData::new);
        return new TradehallManager(saveFile, worldNameManager);
    }
}
