package dev.gxlg.librgetter.savefiles.access;

import dev.gxlg.librgetter.notifier.Notifier;
import dev.gxlg.librgetter.savefiles.SaveFilePathManager;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Supplier;

public abstract class DimensionManagerAccessor<T, M> extends AbstractManagerAccessor<SaveFilePathManager.DimensionKey, T, M> {
    public DimensionManagerAccessor(SaveFilePathManager saveFilePathManager, Notifier notifier, String filename, Class<T> type, Supplier<T> defaultDataSupplier) {
        super(saveFilePathManager, notifier, filename, type, defaultDataSupplier);
    }

    @Override
    protected SaveFilePathManager.DimensionKey getCurrentKeyInternal() {
        return saveFilePathManager.getCurrentDimension();
    }

    @Override
    protected String remapKeyToString(SaveFilePathManager.DimensionKey key) {
        return key.stringKey();
    }

    @Override
    protected List<SaveFilePathManager.DimensionKey> getKeyList() {
        return saveFilePathManager.listAllDimensions();
    }

    @Override
    protected Path getSavePathForKey(SaveFilePathManager.DimensionKey key) {
        return saveFilePathManager.getDimensionSavePath(key);
    }
}
