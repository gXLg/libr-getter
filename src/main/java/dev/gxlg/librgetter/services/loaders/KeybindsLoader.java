package dev.gxlg.librgetter.services.loaders;

import dev.gxlg.librgetter.config.ConfigManager;
import dev.gxlg.librgetter.controller.SharedController;
import dev.gxlg.librgetter.keybinds.KeybindManager;
import dev.gxlg.librgetter.services.ServiceLoader;

import java.util.function.Supplier;

public class KeybindsLoader extends ServiceLoader<KeybindsLoader> {
    private final Supplier<ConfigManager> dependencyConfigManager;

    private final Supplier<String> dependencyModId;

    private final Supplier<String> dependencyModVersion;

    private final Supplier<SharedController> dependencySharedController;

    public KeybindsLoader(CoreLoader coreLoader, SaveFileLoader saveFileLoader, SharedControllerLoader sharedControllerLoader) {
        dependencyModId = initDependency(coreLoader, CoreLoader.exportModId);
        dependencyModVersion = initDependency(coreLoader, CoreLoader.exportModVersion);
        dependencyConfigManager = initDependency(saveFileLoader, SaveFileLoader.exportConfigManager);
        dependencySharedController = initDependency(sharedControllerLoader, SharedControllerLoader.exportSharedController);
    }

    @Override
    public void init() {
        KeybindManager keybindManager = new KeybindManager(dependencyModId.get(), dependencyConfigManager.get(), dependencyModVersion.get(), dependencySharedController.get());
        keybindManager.register();
    }
}
