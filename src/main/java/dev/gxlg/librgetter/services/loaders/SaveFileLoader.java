package dev.gxlg.librgetter.services.loaders;

import dev.gxlg.librgetter.notifier.Notifier;
import dev.gxlg.librgetter.savefiles.SaveFilePathManager;
import dev.gxlg.librgetter.savefiles.config.ConfigManager;
import dev.gxlg.librgetter.savefiles.goals.GoalListAccessor;
import dev.gxlg.librgetter.savefiles.tradehalls.TradehallAccessor;
import dev.gxlg.librgetter.services.ServiceLoader;
import dev.gxlg.librgetter.services.types.Export;

import java.util.function.Supplier;

public class SaveFileLoader extends ServiceLoader<SaveFileLoader> {
    public static final Export<SaveFileLoader, ConfigManager> exportConfigManager = new Export<>(s -> s.configManager);

    public static final Export<SaveFileLoader, GoalListAccessor> exportGoalListAccessor = new Export<>(s -> s.goalListAccessor);

    public static final Export<SaveFileLoader, TradehallAccessor> exportTradehallAccessor = new Export<>(s -> s.tradehallAccessor);

    private final Supplier<String> dependencyModId;

    private final Supplier<Notifier> dependencyNotifier;

    private ConfigManager configManager;

    private GoalListAccessor goalListAccessor;

    private TradehallAccessor tradehallAccessor;

    public SaveFileLoader(CoreLoader coreLoader, NotifierLoader notifierLoader) {
        dependencyModId = initDependency(coreLoader, CoreLoader.exportModId);
        dependencyNotifier = initDependency(notifierLoader, NotifierLoader.exportNotifier);
    }

    @Override
    public void init() {
        String modId = dependencyModId.get();
        Notifier notifier = dependencyNotifier.get();

        SaveFilePathManager saveFileManager = new SaveFilePathManager(modId, notifier);

        configManager = saveFileManager.createGlobalManager(p -> ConfigManager.init(p, notifier));
        goalListAccessor = new GoalListAccessor(saveFileManager, notifier);
        tradehallAccessor = new TradehallAccessor(saveFileManager, notifier);
    }
}