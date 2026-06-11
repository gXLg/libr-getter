package dev.gxlg.librgetter.services.loaders;

import dev.gxlg.librgetter.services.ServiceLoader;
import dev.gxlg.librgetter.services.types.Export;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class CoreLoader extends ServiceLoader<CoreLoader> {
    public static final Export<CoreLoader, String> exportModId = new Export<>(c -> c.modId);

    public static final Export<CoreLoader, String> exportModVersion = new Export<>(c -> c.modVersion);

    @SuppressWarnings("unused")
    public static final Export<CoreLoader, Logger> exportLogger = new Export<>(c -> c.logger);

    private String modId;

    private String modVersion;

    private Logger logger;

    @Override
    public void init() {
        modId = "librgetter";

        Optional<ModContainer> container = FabricLoader.getInstance().getModContainer(modId);
        modVersion = container.map(modContainer -> "v" + modContainer.getMetadata().getVersion().getFriendlyString()).orElse(null);

        logger = LogManager.getLogger(modId);
        logger.info("Hello World from LibrGetter!");
    }
}
