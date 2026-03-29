package dev.gxlg.librgetter.services.loaders;

import dev.gxlg.librgetter.notifier.Notifier;
import dev.gxlg.librgetter.services.Export;
import dev.gxlg.librgetter.services.ServiceLoader;
import dev.gxlg.librgetter.utils.config.ConfigManager;

import java.nio.file.Path;
import java.util.function.Supplier;

public class ConfigLoader extends ServiceLoader<ConfigLoader> {
    public static final Export<ConfigLoader, ConfigManager> exportConfigManager = new Export<>(c -> c.configManager);

    private final Supplier<Path> dependencyConfigPath;

    private final Supplier<Notifier> dependencyNotifier;

    private ConfigManager configManager = null;

    public ConfigLoader(CoreLoader coreLoader, NotifierLoader notifierLoader) {
        dependencyConfigPath = initDependency(coreLoader, CoreLoader.exportConfigPath);
        dependencyNotifier = initDependency(notifierLoader, NotifierLoader.exportNotifier);
    }

    @Override
    public void init() {
        configManager = ConfigManager.init(dependencyConfigPath.get(), dependencyNotifier.get());
    }
}
