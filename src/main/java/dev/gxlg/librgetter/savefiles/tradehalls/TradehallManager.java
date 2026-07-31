package dev.gxlg.librgetter.savefiles.tradehalls;

import dev.gxlg.librgetter.savefiles.JsonSaveFile;
import dev.gxlg.librgetter.savefiles.SaveFileManager;
import dev.gxlg.librgetter.savefiles.WorldNameManager;
import dev.gxlg.librgetter.utils.exceptions.common.InternalErrorException;
import dev.gxlg.versiont.gen.net.minecraft.core.BlockPos;

import java.util.ArrayList;

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

    public boolean isLecternSaved(BlockPos lecternPos) throws InternalErrorException {
        String worldName = worldNameManager.getWorldName();
        if (worldName == null) {
            throw new InternalErrorException("worldName");
        }
        if (!data.tradehalls.containsKey(worldName)) {
            return false;
        }
        return data.tradehalls.get(worldName).contains(posToString(lecternPos));
    }

    public void saveLectern(BlockPos lecternPos) throws InternalErrorException {
        String worldName = worldNameManager.getWorldName();
        if (worldName == null) {
            throw new InternalErrorException("worldName");
        }
        data.tradehalls.computeIfAbsent(worldName, k -> new ArrayList<>()).add(posToString(lecternPos));
    }

    public void clearWorld() throws InternalErrorException {
        String worldName = worldNameManager.getWorldName();
        if (worldName == null) {
            throw new InternalErrorException("worldName");
        }
        data.tradehalls.remove(worldName);
    }

    public void clearAll() {
        data.tradehalls.clear();
    }

    public void save() {
        this.saveFile.save();
    }

    public static TradehallManager init(SaveFileManager saveFileManager, WorldNameManager worldNameManager) {
        JsonSaveFile<TradehallData> saveFile = saveFileManager.createSaveFile(FILENAME, TradehallData.class, TradehallData::new);
        return new TradehallManager(saveFile, worldNameManager);
    }

    public static String posToString(BlockPos pos) {
        return pos.getX() + "," + pos.getY() + "," + pos.getZ();
    }
}
