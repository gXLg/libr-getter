package dev.gxlg.librgetter.savefiles;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.gxlg.librgetter.notifier.Notifier;
import dev.gxlg.librgetter.utils.messages.translatable.error.CouldNotInitSaveFileMessage;
import dev.gxlg.librgetter.utils.messages.translatable.error.CouldNotReadSaveFileMessage;
import dev.gxlg.librgetter.utils.messages.translatable.error.CouldNotSaveSaveFileMessage;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.function.Supplier;

public class JsonSaveFile<T> {
    private final String fileName;

    private final Path filePath;

    private final T data;

    private final Notifier notifier;

    protected JsonSaveFile(Path filePath, String fileName, T data, Notifier notifier) {
        this.filePath = filePath;
        this.fileName = fileName;
        this.data = data;
        this.notifier = notifier;
        save();
    }

    public T getData() {
        return data;
    }

    public void save() {
        try {
            Path tempPath = filePath.resolveSibling(filePath.getFileName() + ".tmp");
            if (!Files.exists(tempPath)) {
                Files.createFile(tempPath);
            }

            Files.write(tempPath, GSON.toJson(this.data).getBytes(), StandardOpenOption.WRITE);
            Files.move(tempPath, filePath, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            notifier.addNotification(new CouldNotSaveSaveFileMessage(fileName));
        }
    }

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static <T> JsonSaveFile<T> init(Notifier notifier, Path savePath, String fileName, Class<T> type, Supplier<T> defaultDataSupplier) {
        if (savePath == null) {
            return new DummySaveFile<>(defaultDataSupplier.get());
        }

        Path filePath = savePath.resolve(fileName);
        T data;
        if (Files.notExists(filePath)) {
            try {
                Files.createFile(filePath);
                data = defaultDataSupplier.get();
            } catch (IOException e) {
                notifier.addNotification(new CouldNotInitSaveFileMessage(fileName));
                return new DummySaveFile<>(defaultDataSupplier.get());
            }
        } else {
            try (FileReader reader = new FileReader(filePath.toFile())) {
                data = GSON.fromJson(reader, type);
            } catch (IOException e) {
                notifier.addNotification(new CouldNotReadSaveFileMessage(fileName));
                data = defaultDataSupplier.get();
            }
        }
        return new JsonSaveFile<>(filePath, fileName, data, notifier);
    }
}
