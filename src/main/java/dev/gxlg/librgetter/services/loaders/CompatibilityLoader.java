package dev.gxlg.librgetter.services.loaders;

import dev.gxlg.librgetter.compatibility.CompatibilityManager;
import dev.gxlg.librgetter.services.Export;
import dev.gxlg.librgetter.services.ServiceLoader;
import dev.gxlg.librgetter.utils.config.ConfigManager;

import java.util.function.Supplier;

public class CompatibilityLoader extends ServiceLoader<CompatibilityLoader> {
    public static final Export<CompatibilityLoader, CompatibilityManager> exportCompatibilityManager = new Export<>(c -> c.compatibilityManager);

    private final Supplier<ConfigManager> dependencyConfigManager;

    private CompatibilityManager compatibilityManager;

    public CompatibilityLoader(ConfigLoader configLoader) {
        dependencyConfigManager = initDependency(configLoader, ConfigLoader.exportConfigManager);
    }

    @Override
    public void init() {
        compatibilityManager = new CompatibilityManager(dependencyConfigManager.get());
    }
}
