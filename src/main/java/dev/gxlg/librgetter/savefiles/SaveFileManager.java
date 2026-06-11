package dev.gxlg.librgetter.savefiles;

import dev.gxlg.librgetter.notifier.Notifier;
import dev.gxlg.librgetter.utils.messages.translatable.error.CouldNotInitSaveFileDirectoryMessage;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Supplier;

public class SaveFileManager {
    private final Path savePath;

    private final Notifier notifier;

    public SaveFileManager(String modId, Notifier notifier) {
        Path savePath = FabricLoader.getInstance().getConfigDir().resolve(modId);

        if (Files.exists(savePath)) {
            if (!Files.isDirectory(savePath)) {
                notifier.addNotification(new CouldNotInitSaveFileDirectoryMessage());
                savePath = null;
            }
        } else {
            try {
                Files.createDirectory(savePath);
            } catch (IOException e) {
                notifier.addNotification(new CouldNotInitSaveFileDirectoryMessage());
                savePath = null;
            }
        }

        this.savePath = savePath;
        this.notifier = notifier;
    }

    public <T> JsonSaveFile<T> createSaveFile(String filename, Class<T> type, Supplier<T> defaultDataSupplier) {
        return JsonSaveFile.init(notifier, savePath, filename, type, defaultDataSupplier);
    }
}
