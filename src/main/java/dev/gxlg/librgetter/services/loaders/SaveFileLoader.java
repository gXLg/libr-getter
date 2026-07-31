package dev.gxlg.librgetter.services.loaders;

import dev.gxlg.librgetter.notifier.Notifier;
import dev.gxlg.librgetter.savefiles.SaveFileManager;
import dev.gxlg.librgetter.savefiles.WorldNameManager;
import dev.gxlg.librgetter.savefiles.config.ConfigManager;
import dev.gxlg.librgetter.savefiles.goals.GoalListManager;
import dev.gxlg.librgetter.savefiles.tradehalls.TradehallManager;
import dev.gxlg.librgetter.services.ServiceLoader;
import dev.gxlg.librgetter.services.types.Export;

import java.util.function.Supplier;

public class SaveFileLoader extends ServiceLoader<SaveFileLoader> {
    public static final Export<SaveFileLoader, ConfigManager> exportConfigManager = new Export<>(s -> s.configManager);

    public static final Export<SaveFileLoader, GoalListManager> exportGoalListManager = new Export<>(s -> s.goalListManager);

    public static final Export<SaveFileLoader, TradehallManager> exportTradehallManager = new Export<>(s -> s.tradehallManager);

    private final Supplier<String> dependencyModId;

    private final Supplier<Notifier> dependencyNotifier;

    private final Supplier<WorldNameManager> dependencyWorldNameManager;

    private ConfigManager configManager;

    private GoalListManager goalListManager;

    private TradehallManager tradehallManager;

    public SaveFileLoader(CoreLoader coreLoader, NotifierLoader notifierLoader, WorldNameLoader worldNameLoader) {
        dependencyModId = initDependency(coreLoader, CoreLoader.exportModId);
        dependencyNotifier = initDependency(notifierLoader, NotifierLoader.exportNotifier);
        dependencyWorldNameManager = initDependency(worldNameLoader, WorldNameLoader.exportWorldNameManager);
    }

    @Override
    public void init() {
        String modId = dependencyModId.get();
        Notifier notifier = dependencyNotifier.get();
        WorldNameManager worldNameManager = dependencyWorldNameManager.get();

        SaveFileManager saveFileManager = new SaveFileManager(modId, notifier);

        configManager = ConfigManager.init(saveFileManager, notifier);
        goalListManager = GoalListManager.init(saveFileManager);
        tradehallManager = TradehallManager.init(saveFileManager, worldNameManager);
    }
}