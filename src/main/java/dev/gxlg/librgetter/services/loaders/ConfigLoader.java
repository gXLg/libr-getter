package dev.gxlg.librgetter.services.loaders;

import dev.gxlg.librgetter.services.Export;
import dev.gxlg.librgetter.services.ServiceLoader;
import dev.gxlg.librgetter.utils.config.ConfigManager;

import java.nio.file.Path;
import java.util.function.Supplier;

public class ConfigLoader extends ServiceLoader<ConfigLoader> {
    public static final Export<ConfigLoader, ConfigManager> exportConfigManager = new Export<>(c -> c.configManager);

    private final Supplier<Path> dependencyConfigPath;

    private ConfigManager configManager = null;

    public ConfigLoader(CoreLoader coreLoader) {
        dependencyConfigPath = initDependency(coreLoader, CoreLoader.exportConfigPath);
    }

    @Override
    public void init() {
        configManager = ConfigManager.init(dependencyConfigPath.get());
    }
}
