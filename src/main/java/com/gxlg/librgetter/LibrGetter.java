package com.gxlg.librgetter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.gxlg.librgetter.utils.Commands;
import com.gxlg.librgetter.utils.Keybinds;
import com.gxlg.librgetter.utils.Messages;
import com.gxlg.librgetter.utils.MultiVersion;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.network.ClientPlayerEntity;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("BlockingMethodInNonBlockingContext")
public class LibrGetter implements ClientModInitializer {

    public static final String MOD_ID = "librgetter";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static Config config;
    public static Pair<String, String> newVersion;
    private static Path confPath;

    @Override
    public void onInitializeClient() {
        if (!MultiVersion.isApiLevel(MultiVersion.ApiLevel.BASE)) {
            throw new RuntimeException("This version is not supported by LibrGetter!");
        }
        LOGGER.info("Hello World from LibrGetter! Running on {}", MultiVersion.getVersion());

        Commands.registerCommand();

        initConfig();

        // registering keybinds
        Keybinds.registerKeybinds();

        // checking for a new update
        if (config.checkUpdate) {
            CompletableFuture.runAsync(() -> {
                try {
                    URL url = URI.create("https://api.github.com/repos/gXLg/libr-getter/releases/latest").toURL();
                    InputStreamReader reader = new InputStreamReader(url.openStream());
                    JsonObject data = new Gson().fromJson(reader, JsonObject.class);
                    reader.close();

                    String version = getVersion();
                    if (version == null) return;
                    String newest = data.get("tag_name").getAsString();

                    if (!newest.equals(version)) {
                        newVersion = new ImmutablePair<>(newest + " - " + data.get("name").getAsString(), data.get("body").getAsString().replace("\r", ""));
                    }
                } catch (IOException ignored) {
                }
            });

            // notifying about update
            ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
                ClientPlayerEntity player = client.player;
                if (newVersion != null) {
                    Messages.newVersion(player, newVersion.getLeft(), newVersion.getRight());
                    newVersion = null;
                }
            });
        }
    }

    public static String getVersion() {
        Optional<ModContainer> container = FabricLoader.getInstance().getModContainer(MOD_ID);
        return container.map(modContainer -> "v" + modContainer.getMetadata().getVersion().getFriendlyString())
                .orElse(null);
    }

    private void initConfig() {
        Path configPath = FabricLoader.getInstance().getConfigDir().resolve("librgetter.json");
        if (Files.notExists(configPath)) {
            try {
                Files.createFile(configPath);
            } catch (IOException e) {
                throw new RuntimeException("Could not initialize config", e);
            }
            confPath = configPath;
            config = new Config();
        } else {
            try (FileReader reader = new FileReader(configPath.toFile())) {
                confPath = configPath;
                config = GSON.fromJson(reader, Config.class);
            } catch (IOException e) {
                throw new RuntimeException("Could not parse config", e);
            }
        }
        saveConfigs();
    }

    public static void saveConfigs() {
        Path dir = confPath.getParent();

        try {
            if (Files.notExists(dir)) {
                Files.createDirectory(dir);
            } else if (!Files.isDirectory(dir)) {
                throw new IOException("Not a directory: " + dir);
            }

            Path tempPath = confPath.resolveSibling(confPath.getFileName() + ".tmp");
            Files.createFile(tempPath);
            Files.write(tempPath, GSON.toJson(config).getBytes(), StandardOpenOption.WRITE);
            Files.move(tempPath, confPath, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Could not save config", e);
        }
    }
}