package dev.gxlg.librgetter.services.loaders;

import dev.gxlg.librgetter.controller.SharedController;
import dev.gxlg.librgetter.keybinds.KeybindManager;
import dev.gxlg.librgetter.savefiles.config.ConfigManager;
import dev.gxlg.librgetter.savefiles.goals.GoalListManager;
import dev.gxlg.librgetter.services.ServiceLoader;

import java.util.function.Supplier;

public class KeybindsLoader extends ServiceLoader<KeybindsLoader> {
    private final Supplier<ConfigManager> dependencyConfigManager;

    private final Supplier<GoalListManager> dependencyGoalListManager;

    private final Supplier<String> dependencyModId;

    private final Supplier<String> dependencyModVersion;

    private final Supplier<SharedController> dependencySharedController;

    public KeybindsLoader(CoreLoader coreLoader, SaveFileLoader saveFileLoader, SharedControllerLoader sharedControllerLoader) {
        dependencyModId = initDependency(coreLoader, CoreLoader.exportModId);
        dependencyModVersion = initDependency(coreLoader, CoreLoader.exportModVersion);
        dependencyConfigManager = initDependency(saveFileLoader, SaveFileLoader.exportConfigManager);
        dependencyGoalListManager = initDependency(saveFileLoader, SaveFileLoader.exportGoalListManager);
        dependencySharedController = initDependency(sharedControllerLoader, SharedControllerLoader.exportSharedController);
    }

    @Override
    public void init() {
        String modId = dependencyModId.get();
        String modVersion = dependencyModVersion.get();
        ConfigManager configManager = dependencyConfigManager.get();
        GoalListManager goalListManager = dependencyGoalListManager.get();
        SharedController sharedController = dependencySharedController.get();

        KeybindManager keybindManager = new KeybindManager(modId, configManager, goalListManager, modVersion, sharedController);
        keybindManager.register();
    }
}
