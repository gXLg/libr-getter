package dev.gxlg.librgetter;

import dev.gxlg.librgetter.gui.impl.config.ConfigScreen;
import dev.gxlg.librgetter.gui.impl.goals.AbstractDynamicWidgetScreen;
import dev.gxlg.librgetter.gui.impl.goals.add.AbstractAddGoalScreen;
import dev.gxlg.librgetter.gui.impl.goals.add.AddCustomGoalScreen;
import dev.gxlg.librgetter.gui.impl.goals.add.AddGoalScreen;
import dev.gxlg.librgetter.gui.impl.goals.list.GoalListEntry;
import dev.gxlg.librgetter.gui.impl.goals.list.GoalListScreen;
import dev.gxlg.librgetter.gui.impl.goals.select.EnchantmentListEntry;
import dev.gxlg.librgetter.gui.impl.goals.select.SelectEnchantmentScreen;
import dev.gxlg.librgetter.gui.impl.tradehall.TradehallEntry;
import dev.gxlg.librgetter.gui.impl.tradehall.TradehallScreen;
import dev.gxlg.librgetter.gui.lib.widgets.list.CustomSelectionList;
import dev.gxlg.librgetter.gui.lib.widgets.list.CustomSelectionListEntry;
import dev.gxlg.librgetter.gui.lib.widgets.list.CustomSelectionList_1_20_3;
import dev.gxlg.librgetter.gui.lib.widgets.unified.editbox.LegacyEditBox;
import dev.gxlg.librgetter.gui.lib.widgets.unified.string.LegacyStringWidget;
import dev.gxlg.librgetter.services.ServiceLoaderManager;
import dev.gxlg.librgetter.services.loaders.CommandsLoader;
import dev.gxlg.librgetter.services.loaders.CompatibilityLoader;
import dev.gxlg.librgetter.services.loaders.CoreLoader;
import dev.gxlg.librgetter.services.loaders.KeybindsLoader;
import dev.gxlg.librgetter.services.loaders.MixinImplLoader;
import dev.gxlg.librgetter.services.loaders.NotifierLoader;
import dev.gxlg.librgetter.services.loaders.ParticleSpawnerLoader;
import dev.gxlg.librgetter.services.loaders.SaveFileLoader;
import dev.gxlg.librgetter.services.loaders.SharedControllerLoader;
import dev.gxlg.librgetter.services.loaders.TradehallScannerLoader;
import dev.gxlg.librgetter.services.loaders.UpdaterLoader;
import dev.gxlg.librgetter.services.loaders.WorkerLoader;
import dev.gxlg.versiont.api.R;
import net.fabricmc.api.ClientModInitializer;

public class Entrypoint implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // preload

        R.preload(
            ConfigScreen.clazz,
            AbstractAddGoalScreen.clazz,
            AddCustomGoalScreen.clazz,
            AddGoalScreen.clazz,
            GoalListEntry.clazz,
            GoalListScreen.clazz,
            EnchantmentListEntry.clazz,
            SelectEnchantmentScreen.clazz,
            TradehallEntry.clazz,
            TradehallScreen.clazz,
            AbstractDynamicWidgetScreen.clazz,
            CustomSelectionList.clazz,
            CustomSelectionList_1_20_3.clazz,
            CustomSelectionListEntry.clazz,
            LegacyEditBox.clazz,
            LegacyStringWidget.clazz
        );

        // init services

        ServiceLoaderManager loaderManager = new ServiceLoaderManager();

        CoreLoader coreLoader = new CoreLoader();
        loaderManager.registerServiceLoader(coreLoader);

        NotifierLoader notifierLoader = new NotifierLoader();
        loaderManager.registerServiceLoader(notifierLoader);

        SaveFileLoader saveFileLoader = new SaveFileLoader(coreLoader, notifierLoader);
        loaderManager.registerServiceLoader(saveFileLoader);

        TradehallScannerLoader tradehallScannerLoader = new TradehallScannerLoader(saveFileLoader);
        loaderManager.registerServiceLoader(tradehallScannerLoader);

        CompatibilityLoader compatibilityLoader = new CompatibilityLoader(saveFileLoader);
        loaderManager.registerServiceLoader(compatibilityLoader);

        WorkerLoader workerLoader = new WorkerLoader(saveFileLoader, compatibilityLoader);
        loaderManager.registerServiceLoader(workerLoader);

        ParticleSpawnerLoader particleSpawnerLoader = new ParticleSpawnerLoader(workerLoader);
        loaderManager.registerServiceLoader(particleSpawnerLoader);

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
