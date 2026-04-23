package dev.gxlg.librgetter.services.loaders;

import dev.gxlg.librgetter.config.ConfigManager;
import dev.gxlg.librgetter.notifier.Notifier;
import dev.gxlg.librgetter.services.ServiceLoader;
import dev.gxlg.librgetter.utils.Updater;

import java.util.function.Supplier;

public class UpdaterLoader extends ServiceLoader<UpdaterLoader> {
    private final Supplier<String> dependencyModVersion;

    private final Supplier<Notifier> dependencyNotifier;

    private final Supplier<ConfigManager> dependencyConfigManager;

    public UpdaterLoader(CoreLoader coreLoader, NotifierLoader notifierLoader, SaveFileLoader saveFileLoader) {
        dependencyModVersion = initDependency(coreLoader, CoreLoader.exportModVersion);
        dependencyNotifier = initDependency(notifierLoader, NotifierLoader.exportNotifier);
        dependencyConfigManager = initDependency(saveFileLoader, SaveFileLoader.exportConfigManager);
    }

    @Override
    public void init() {
        Updater.checkUpdates(dependencyNotifier.get(), dependencyConfigManager.get(), dependencyModVersion.get());
    }
}
