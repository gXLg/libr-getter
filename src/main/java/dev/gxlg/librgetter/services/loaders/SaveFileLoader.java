package dev.gxlg.librgetter.services.loaders;

import dev.gxlg.librgetter.config.ConfigManager;
import dev.gxlg.librgetter.goals.GoalListManager;
import dev.gxlg.librgetter.notifier.Notifier;
import dev.gxlg.librgetter.savefiles.SaveFileManager;
import dev.gxlg.librgetter.services.ServiceLoader;
import dev.gxlg.librgetter.services.types.Export;

import java.util.function.Supplier;

public class SaveFileLoader extends ServiceLoader<SaveFileLoader> {
    public static final Export<SaveFileLoader, ConfigManager> exportConfigManager = new Export<>(s -> s.configManager);

    public static final Export<SaveFileLoader, GoalListManager> exportGoalListManager = new Export<>(s -> s.goalListManager);

    private final Supplier<String> dependencyModId;

    private final Supplier<Notifier> dependencyNotifier;

    private ConfigManager configManager;

    private GoalListManager goalListManager;

    public SaveFileLoader(CoreLoader coreLoader, NotifierLoader notifierLoader) {
        dependencyModId = initDependency(coreLoader, CoreLoader.exportModId);
        dependencyNotifier = initDependency(notifierLoader, NotifierLoader.exportNotifier);
    }

    @Override
    public void init() {
        String modId = dependencyModId.get();
        Notifier notifier = dependencyNotifier.get();

        SaveFileManager saveFileManager = new SaveFileManager(modId, notifier);

        configManager = ConfigManager.init(saveFileManager, notifier);
        goalListManager = GoalListManager.init(saveFileManager);
    }
}