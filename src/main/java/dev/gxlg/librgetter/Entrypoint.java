package dev.gxlg.librgetter;

import dev.gxlg.librgetter.gui.ConfigScreen;
import dev.gxlg.librgetter.services.ServiceLoaderManager;
import dev.gxlg.librgetter.services.loaders.CommandsLoader;
import dev.gxlg.librgetter.services.loaders.CompatibilityLoader;
import dev.gxlg.librgetter.services.loaders.ConfigLoader;
import dev.gxlg.librgetter.services.loaders.CoreLoader;
import dev.gxlg.librgetter.services.loaders.KeybindsLoader;
import dev.gxlg.librgetter.services.loaders.MixinImplLoader;
import dev.gxlg.librgetter.services.loaders.NotifierLoader;
import dev.gxlg.librgetter.services.loaders.UpdaterLoader;
import dev.gxlg.librgetter.services.loaders.WorkerLoader;
import dev.gxlg.versiont.api.R;
import net.fabricmc.api.ClientModInitializer;

public class Entrypoint implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // preload

        R.preload(ConfigScreen.clazz);


        // init services

        ServiceLoaderManager loaderManager = new ServiceLoaderManager();

        CoreLoader coreLoader = new CoreLoader();
        loaderManager.registerServiceLoader(coreLoader);

        NotifierLoader notifierLoader = new NotifierLoader();
        loaderManager.registerServiceLoader(notifierLoader);

        ConfigLoader configLoader = new ConfigLoader(coreLoader);
        loaderManager.registerServiceLoader(configLoader);

        CompatibilityLoader compatibilityLoader = new CompatibilityLoader(configLoader);
        loaderManager.registerServiceLoader(compatibilityLoader);

        WorkerLoader workerLoader = new WorkerLoader(configLoader, compatibilityLoader);
        loaderManager.registerServiceLoader(workerLoader);

        CommandsLoader commandsLoader = new CommandsLoader(configLoader, workerLoader);
        loaderManager.registerServiceLoader(commandsLoader);

        KeybindsLoader keybindsLoader = new KeybindsLoader(coreLoader, configLoader);
        loaderManager.registerServiceLoader(keybindsLoader);

        MixinImplLoader mixinImplLoader = new MixinImplLoader(workerLoader, compatibilityLoader);
        loaderManager.registerServiceLoader(mixinImplLoader);

        UpdaterLoader updaterLoader = new UpdaterLoader(coreLoader, notifierLoader, configLoader);
        loaderManager.registerServiceLoader(updaterLoader);

        loaderManager.init();
    }
}
