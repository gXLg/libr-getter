package dev.gxlg.librgetter;

import dev.gxlg.librgetter.utils.Updater;
import dev.gxlg.librgetter.utils.chaining.commands.Commands;
import dev.gxlg.librgetter.utils.chaining.keybinds.Keybinds;
import dev.gxlg.librgetter.utils.config.ConfigData;
import dev.gxlg.librgetter.utils.config.ConfigManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.util.Optional;

public class LibrGetter implements ClientModInitializer {
    public static final String MOD_ID = "librgetter";

    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static final Path configPath = FabricLoader.getInstance().getConfigDir().resolve("librgetter.json");

    public static final ConfigManager configManager = ConfigManager.init(configPath);

    public static final ConfigData config = configManager.getData();

    @Override
    public void onInitializeClient() {
        LOGGER.info("Hello World from LibrGetter!");

        // register commands
        Commands.getImpl().registerCommands();

        // register keybinds
        Keybinds.getImpl().registerKeybinds();

        // checking for a new update
        Updater.checkUpdates();
    }

    public static String getVersion() {
        Optional<ModContainer> container = FabricLoader.getInstance().getModContainer(MOD_ID);
        return container.map(modContainer -> "v" + modContainer.getMetadata().getVersion().getFriendlyString()).orElse(null);
    }
}