package dev.gxlg.librgetter.savefiles.access;

import dev.gxlg.librgetter.notifier.Notifier;
import dev.gxlg.librgetter.savefiles.SaveFilePathManager;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Supplier;

public abstract class WorldManagerAccessor<T, M> extends AbstractManagerAccessor<SaveFilePathManager.WorldInfo, T, M> {
    public WorldManagerAccessor(SaveFilePathManager saveFilePathManager, Notifier notifier, String filename, Class<T> type, Supplier<T> defaultDataSupplier) {
        super(saveFilePathManager, notifier, filename, type, defaultDataSupplier);
    }

    @Override
    protected SaveFilePathManager.WorldInfo getCurrentKeyInternal() {
        return saveFilePathManager.getCurrentWorldInfo();
    }

    @Override
    protected String remapKeyToString(SaveFilePathManager.WorldInfo key) {
        return key.toString();
    }

    @Override
    protected List<SaveFilePathManager.WorldInfo> getKeyList() {
        return saveFilePathManager.listAllWorldInfos();
    }

    @Override
    protected Path getSavePathForKey(SaveFilePathManager.WorldInfo key) {
        return saveFilePathManager.getWorldSavePath(key);
    }
}
