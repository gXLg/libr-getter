package dev.gxlg.librgetter.services.loaders;

import dev.gxlg.librgetter.keybinds.KeybindManager;
import dev.gxlg.librgetter.services.ServiceLoader;
import dev.gxlg.librgetter.utils.config.ConfigManager;

import java.util.function.Supplier;

public class KeybindsLoader extends ServiceLoader<KeybindsLoader> {
    private final Supplier<ConfigManager> dependencyConfigManager;

    private final Supplier<String> dependencyModId;

    private final Supplier<String> dependencyModVersion;

    public KeybindsLoader(CoreLoader coreLoader, ConfigLoader configLoader) {
        dependencyModId = initDependency(coreLoader, CoreLoader.exportModId);
        dependencyModVersion = initDependency(coreLoader, CoreLoader.exportModVersion);
        dependencyConfigManager = initDependency(configLoader, ConfigLoader.exportConfigManager);
    }

    @Override
    public void init() {
        KeybindManager keybindManager = new KeybindManager(dependencyConfigManager.get(), dependencyModVersion.get(), dependencyModId.get());
        keybindManager.register();
    }
}
