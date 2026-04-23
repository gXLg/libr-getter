package dev.gxlg.librgetter;

import dev.gxlg.librgetter.gui.config.ConfigScreen;
import dev.gxlg.librgetter.gui.goals.GoalScreen;
import dev.gxlg.librgetter.services.ServiceLoaderManager;
import dev.gxlg.librgetter.services.loaders.CommandsLoader;
import dev.gxlg.librgetter.services.loaders.CompatibilityLoader;
import dev.gxlg.librgetter.services.loaders.CoreLoader;
import dev.gxlg.librgetter.services.loaders.KeybindsLoader;
import dev.gxlg.librgetter.services.loaders.MixinImplLoader;
import dev.gxlg.librgetter.services.loaders.NotifierLoader;
import dev.gxlg.librgetter.services.loaders.SaveFileLoader;
import dev.gxlg.librgetter.services.loaders.SharedControllerLoader;
import dev.gxlg.librgetter.services.loaders.UpdaterLoader;
import dev.gxlg.librgetter.services.loaders.WorkerLoader;
import dev.gxlg.versiont.api.R;
import net.fabricmc.api.ClientModInitializer;

public class Entrypoint implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // preload

        R.preload(ConfigScreen.clazz, GoalScreen.clazz);

        // init services

        ServiceLoaderManager loaderManager = new ServiceLoaderManager();

        CoreLoader coreLoader = new CoreLoader();
        loaderManager.registerServiceLoader(coreLoader);

        NotifierLoader notifierLoader = new NotifierLoader();
        loaderManager.registerServiceLoader(notifierLoader);

        SaveFileLoader saveFileLoader = new SaveFileLoader(coreLoader, notifierLoader);
        loaderManager.registerServiceLoader(saveFileLoader);

        CompatibilityLoader compatibilityLoader = new CompatibilityLoader(saveFileLoader);
        loaderManager.registerServiceLoader(compatibilityLoader);

        WorkerLoader workerLoader = new WorkerLoader(saveFileLoader, compatibilityLoader);
        loaderManager.registerServiceLoader(workerLoader);

        SharedControllerLoader sharedControllerLoader = new SharedControllerLoader(workerLoader);
        loaderManager.registerServiceLoader(sharedControllerLoader);

        CommandsLoader commandsLoader = new CommandsLoader(saveFileLoader, sharedControllerLoader);
        loaderManager.registerServiceLoader(commandsLoader);

        KeybindsLoader keybindsLoader = new KeybindsLoader(coreLoader, saveFileLoader, sharedControllerLoader);
        loaderManager.registerServiceLoader(keybindsLoader);

        MixinImplLoader mixinImplLoader = new MixinImplLoader(workerLoader, compatibilityLoader);
        loaderManager.registerServiceLoader(mixinImplLoader);

        UpdaterLoader updaterLoader = new UpdaterLoader(coreLoader, notifierLoader, saveFileLoader);
        loaderManager.registerServiceLoader(updaterLoader);

        loaderManager.init();
    }
}
