package dev.gxlg.librgetter.services.loaders;

import dev.gxlg.librgetter.notifier.Notifier;
import dev.gxlg.librgetter.services.ServiceLoader;
import dev.gxlg.librgetter.utils.Updater;
import dev.gxlg.librgetter.utils.config.ConfigManager;

import java.util.function.Supplier;

public class UpdaterLoader extends ServiceLoader<UpdaterLoader> {
    private final Supplier<String> dependencyModVersion;

    private final Supplier<Notifier> dependencyNotifier;

    private final Supplier<ConfigManager> dependencyConfigManager;

    public UpdaterLoader(CoreLoader coreLoader, NotifierLoader notifierLoader, ConfigLoader configLoader) {
        dependencyModVersion = initDependency(coreLoader, CoreLoader.exportModVersion);
        dependencyNotifier = initDependency(notifierLoader, NotifierLoader.exportNotifier);
        dependencyConfigManager = initDependency(configLoader, ConfigLoader.exportConfigManager);
    }

    @Override
    public void init() {
        Updater.checkUpdates(dependencyNotifier.get(), dependencyConfigManager.get(), dependencyModVersion.get());
    }
}
