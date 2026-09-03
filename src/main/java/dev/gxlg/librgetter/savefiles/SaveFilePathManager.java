package dev.gxlg.librgetter.savefiles;

import dev.gxlg.librgetter.notifier.Notifier;
import dev.gxlg.librgetter.utils.chaining.filesystem.Filesystem;
import dev.gxlg.librgetter.utils.messages.translatable.error.CouldNotInitSaveFileDirectoryMessage;
import dev.gxlg.librgetter.utils.messages.translatable.error.InternalErrorMessage;
import dev.gxlg.versiont.gen.net.minecraft.client.Minecraft;
import dev.gxlg.versiont.gen.net.minecraft.client.multiplayer.ClientLevel;
import dev.gxlg.versiont.gen.net.minecraft.resources.ResourceKey;
import dev.gxlg.versiont.gen.net.minecraft.world.level.Level;
import dev.gxlg.versiont.gen.net.minecraft.world.level.storage.LevelResource;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class SaveFilePathManager {
    private final String modId;

    private final Notifier notifier;

    private final Path globalSavePath;

    private final Path serverSavePath;

    public SaveFilePathManager(String modId, Notifier notifier) {
        this.modId = modId;
        this.notifier = notifier;

        Path configPath = FabricLoader.getInstance().getConfigDir();
        this.globalSavePath = ensureFolder(configPath.resolve(modId));

        Path rootPath = FabricLoader.getInstance().getGameDir();
        this.serverSavePath = ensureFolder(rootPath.resolve(modId + "-server-config"));
    }

    public Path getWorldSavePath(WorldInfo worldInfo) {
        if (worldInfo.isServer()) {
            return ensureFolder(serverSavePath.resolve(worldInfo.name()));
        }
        Path saveRoot = Minecraft.getInstance().getLevelSource().getBaseDir();
        return ensureFolder(saveRoot.resolve(worldInfo.name()).resolve(modId + "-world-config"));
    }

    public WorldInfo getCurrentWorldInfo() {
        Minecraft client = Minecraft.getInstance();
        boolean isServer = !client.isLocalServer();
        String name;
        if (isServer) {
            name = sanitizeFileName(client.getCurrentServer().getIpField());
        } else {
            name = client.getSingleplayerServer().getWorldPath(LevelResource.ROOT()).getParent().toFile().getName();
        }
        return new WorldInfo(name, isServer);
    }

    public List<WorldInfo> listAllWorldInfos() {
        List<WorldInfo> worldInfos = new ArrayList<>();
        Minecraft client = Minecraft.getInstance();
        Path saveRoot = client.getLevelSource().getBaseDir();
        try (Stream<Path> stream = Files.list(saveRoot)) {
            stream.filter(Files::isDirectory).forEach(path -> {
                String name = path.getFileName().toString();
                worldInfos.add(new WorldInfo(name, false));
            });
        } catch (IOException e) {
            notifier.addNotification(new CouldNotInitSaveFileDirectoryMessage());
        }
        try (Stream<Path> stream = Files.list(serverSavePath)) {
            stream.filter(Files::isDirectory).forEach(path -> {
                String name = path.getFileName().toString();
                worldInfos.add(new WorldInfo(name, true));
            });
        } catch (IOException e) {
            notifier.addNotification(new CouldNotInitSaveFileDirectoryMessage());
        }
        return Collections.unmodifiableList(worldInfos);
    }

    public Path getDimensionSavePath(DimensionKey dimension) {
        WorldInfo worldInfo = getCurrentWorldInfo();
        if (worldInfo.isServer()) {
            Path root = getWorldSavePath(worldInfo);
            if (root == null) {
                return null;
            }
            return ensureFolder(root.resolve(sanitizeFileName(dimension.stringKey())));
        }
        Path saveRoot = Minecraft.getInstance().getLevelSource().getBaseDir();
        Path worldRoot = saveRoot.resolve(worldInfo.name());
        Path dimensionPath = Filesystem.getDimensionPath(dimension.resourceKey(), worldRoot);
        return ensureFolder(dimensionPath.resolve(modId + "-dimension-config"));
    }

    public DimensionKey getCurrentDimension() {
        Minecraft client = Minecraft.getInstance();
        ClientLevel level = client.getLevelField();
        if (level == null) {
            notifier.addNotification(new InternalErrorMessage("level", "getCurrentDimension()"));
            return null;
        }
        ResourceKey key = level.dimension();
        return new DimensionKey(key, key.identifier().toString());
    }

    public List<DimensionKey> listAllDimensions() {
        Minecraft client = Minecraft.getInstance();
        if (client.isLocalServer()) {
            List<DimensionKey> dimensions = new ArrayList<>();
            for (Level level : client.getSingleplayerServer().getAllLevels()) {
                ResourceKey key = level.dimension();
                dimensions.add(new DimensionKey(key, key.identifier().toString()));
            }
            return dimensions.stream().sorted(Comparator.comparing(DimensionKey::stringKey)).toList();
        }
        Path root = getWorldSavePath(getCurrentWorldInfo());
        DimensionKey current = getCurrentDimension();
        if (root == null) {
            return List.of(current);
        }
        List<DimensionKey> dimensions = new ArrayList<>();
        try (Stream<Path> stream = Files.list(root)) {
            stream.filter(Files::isDirectory).forEach(e -> {
                String name = e.toFile().getName();
                dimensions.add(new DimensionKey(null, name));
            });
        } catch (IOException e) {
            notifier.addNotification(new CouldNotInitSaveFileDirectoryMessage());
            return List.of(getCurrentDimension());
        }
        if (!dimensions.contains(current)) {
            dimensions.add(current);
        }
        return dimensions.stream().sorted(Comparator.comparing(DimensionKey::stringKey)).toList();
    }

    private Path ensureFolder(Path folderPath) {
        if (Files.exists(folderPath)) {
            if (!Files.isDirectory(folderPath)) {
                notifier.addNotification(new CouldNotInitSaveFileDirectoryMessage());
                return null;
            }
        } else {
            Path parent = ensureFolder(folderPath.getParent());
            if (parent == null) {
                notifier.addNotification(new CouldNotInitSaveFileDirectoryMessage());
                return null;
            }
            try {
                Files.createDirectory(folderPath);
            } catch (IOException e) {
                notifier.addNotification(new CouldNotInitSaveFileDirectoryMessage());
                return null;
            }
        }
        return folderPath;
    }

    public <M> M createGlobalManager(Function<Path, M> managerFactory) {
        return managerFactory.apply(globalSavePath);
    }

    public record WorldInfo(String name, boolean isServer) {
        @Override
        public @NotNull String toString() {
            return name + (isServer ? " (server)" : " (local)");
        }
    }

    public static String sanitizeFileName(String filename) {
        return filename.replaceAll("[\\\\/:*?\"<>|]", "_");
    }

    public record DimensionKey(ResourceKey resourceKey, String stringKey) { }
}
