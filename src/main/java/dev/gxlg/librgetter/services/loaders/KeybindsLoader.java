package dev.gxlg.librgetter.services.loaders;

import dev.gxlg.librgetter.controller.SharedController;
import dev.gxlg.librgetter.keybinds.KeybindManager;
import dev.gxlg.librgetter.savefiles.config.ConfigManager;
import dev.gxlg.librgetter.savefiles.goals.GoalListAccessor;
import dev.gxlg.librgetter.savefiles.tradehalls.TradehallAccessor;
import dev.gxlg.librgetter.services.ServiceLoader;

import java.util.function.Supplier;

public class KeybindsLoader extends ServiceLoader<KeybindsLoader> {
    private final Supplier<ConfigManager> dependencyConfigManager;

    private final Supplier<GoalListAccessor> dependencyGoalListAccessor;

    private final Supplier<String> dependencyModId;

    private final Supplier<String> dependencyModVersion;

    private final Supplier<SharedController> dependencySharedController;

    private final Supplier<TradehallAccessor> dependencyTradehallAccessor;

    public KeybindsLoader(CoreLoader coreLoader, SaveFileLoader saveFileLoader, SharedControllerLoader sharedControllerLoader) {
        dependencyModId = initDependency(coreLoader, CoreLoader.exportModId);
        dependencyModVersion = initDependency(coreLoader, CoreLoader.exportModVersion);
        dependencyConfigManager = initDependency(saveFileLoader, SaveFileLoader.exportConfigManager);
        dependencyGoalListAccessor = initDependency(saveFileLoader, SaveFileLoader.exportGoalListAccessor);
        dependencySharedController = initDependency(sharedControllerLoader, SharedControllerLoader.exportSharedController);
        dependencyTradehallAccessor = initDependency(saveFileLoader, SaveFileLoader.exportTradehallAccessor);
    }

    @Override
    public void init() {
        String modId = dependencyModId.get();
        String modVersion = dependencyModVersion.get();
        ConfigManager configManager = dependencyConfigManager.get();
        GoalListAccessor goalListAccessor = dependencyGoalListAccessor.get();
        SharedController sharedController = dependencySharedController.get();
        TradehallAccessor tradehallAccessor = dependencyTradehallAccessor.get();

        KeybindManager keybindManager = new KeybindManager(modId, configManager, goalListAccessor, modVersion, sharedController, tradehallAccessor);
        keybindManager.register();
    }
}
