package dev.gxlg.librgetter.services.loaders;

import dev.gxlg.librgetter.compatibility.CompatibilityManager;
import dev.gxlg.librgetter.savefiles.config.ConfigManager;
import dev.gxlg.librgetter.services.ServiceLoader;
import dev.gxlg.librgetter.services.types.Export;

import java.util.function.Supplier;

public class CompatibilityLoader extends ServiceLoader<CompatibilityLoader> {
    public static final Export<CompatibilityLoader, CompatibilityManager> exportCompatibilityManager = new Export<>(c -> c.compatibilityManager);

    private final Supplier<ConfigManager> dependencyConfigManager;

    private CompatibilityManager compatibilityManager;

    public CompatibilityLoader(SaveFileLoader saveFileLoader) {
        dependencyConfigManager = initDependency(saveFileLoader, SaveFileLoader.exportConfigManager);
    }

    @Override
    public void init() {
        compatibilityManager = new CompatibilityManager(dependencyConfigManager.get());
    }
}
