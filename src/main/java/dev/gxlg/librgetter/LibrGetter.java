package dev.gxlg.librgetter;

import dev.gxlg.librgetter.utils.Updater;
import dev.gxlg.librgetter.utils.reflection.Commands;
import dev.gxlg.librgetter.utils.reflection.Keybinds;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

@SuppressWarnings("BlockingMethodInNonBlockingContext")
public class LibrGetter implements ClientModInitializer {

    public static final String MOD_ID = "librgetter";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static Config config;

    @Override
    public void onInitializeClient() {
        LOGGER.info("Hello World from LibrGetter!");

        // initialize configuration
        config = Config.init();

        // register commands
        Commands.registerCommand();

        // register keybinds
        Keybinds.registerKeybinds();

        // checking for a new update
        if (config.checkUpdate) {
            Updater.checkUpdates();
        }
    }

    public static String getVersion() {
        Optional<ModContainer> container = FabricLoader.getInstance().getModContainer(MOD_ID);
        return container.map(modContainer -> "v" + modContainer.getMetadata().getVersion().getFriendlyString()).orElse(null);
    }
}