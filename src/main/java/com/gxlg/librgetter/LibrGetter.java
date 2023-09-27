package com.gxlg.librgetter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gxlg.librgetter.command.LibrGetCommand;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;

public class LibrGetter implements ClientModInitializer {
    public static final String MOD_ID = "librgetter";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static Path confPath;
    public static Config config;

    @Override
    public void onInitializeClient() {

        ClientCommandRegistrationCallback.EVENT.register(LibrGetCommand::register);
        LOGGER.info("Hello World from LibrGetter!");

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
            Files.write(tempPath, GSON.toJson(config).getBytes(StandardCharsets.UTF_8), StandardOpenOption.WRITE);
            Files.move(tempPath, confPath, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Could not save config", e);
        }
    }
}
