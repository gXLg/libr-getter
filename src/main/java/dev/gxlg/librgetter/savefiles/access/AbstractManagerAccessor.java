package dev.gxlg.librgetter.savefiles.access;

import dev.gxlg.librgetter.notifier.Notifier;
import dev.gxlg.librgetter.savefiles.DummySaveFile;
import dev.gxlg.librgetter.savefiles.JsonSaveFile;
import dev.gxlg.librgetter.savefiles.SaveFilePathManager;
import dev.gxlg.librgetter.utils.messages.translatable.error.CouldNotInitSaveFileMessage;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class AbstractManagerAccessor<K, T, M> {
    protected final SaveFilePathManager saveFilePathManager;

    private final Notifier notifier;

    private final String filename;

    private final Class<T> type;

    private final Supplier<T> defaultDataSupplier;

    public AbstractManagerAccessor(SaveFilePathManager saveFilePathManager, Notifier notifier, String filename, Class<T> type, Supplier<T> defaultDataSupplier) {
        this.saveFilePathManager = saveFilePathManager;
        this.notifier = notifier;
        this.filename = filename;
        this.type = type;
        this.defaultDataSupplier = defaultDataSupplier;
    }

    protected abstract K getCurrentKeyInternal();

    protected abstract String remapKeyToString(K key);

    protected abstract List<K> getKeyList();

    protected abstract Path getSavePathForKey(K key);

    protected abstract M createManagerForSaveFile(JsonSaveFile<T> saveFile);

    public M createAccessForCurrentManager() {
        return createManager(getCurrentKeyInternal());
    }

    public String getCurrentAccessKey() {
        return remapKeyToString(getCurrentKeyInternal());
    }

    public Map<String, M> createAccessForAllManagers() {
        return getKeyList().stream().collect(Collectors.toMap(this::remapKeyToString, this::createManager));
    }

    private M createManager(K key) {
        if (key == null) {
            notifier.addNotification(new CouldNotInitSaveFileMessage(filename));
            return createManagerForSaveFile(new DummySaveFile<>(defaultDataSupplier.get()));
        }
        Path savePath = getSavePathForKey(key);
        JsonSaveFile<T> saveFile = JsonSaveFile.init(notifier, savePath, filename, type, defaultDataSupplier);
        return createManagerForSaveFile(saveFile);
    }
}
