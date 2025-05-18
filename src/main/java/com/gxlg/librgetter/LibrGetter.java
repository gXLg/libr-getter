package com.gxlg.librgetter;

import com.gxlg.librgetter.utils.Keybinds;
import com.gxlg.librgetter.utils.MultiVersion;
import com.gxlg.librgetter.utils.Updater;
import com.gxlg.librgetter.utils.reflection.Commands;
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
        if (!MultiVersion.isApiLevel(MultiVersion.ApiLevel.BASE)) {
            throw new RuntimeException("This version is not supported by LibrGetter!");
        }
        LOGGER.info("Hello World from LibrGetter! Running on {}", MultiVersion.getVersion());

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