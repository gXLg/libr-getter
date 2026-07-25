package dev.gxlg.librgetter;

import dev.gxlg.librgetter.gui.config.ConfigScreen;
import dev.gxlg.librgetter.gui.goals.AbstractDynamicWidgetScreen;
import dev.gxlg.librgetter.gui.goals.add.AbstractAddGoalScreen;
import dev.gxlg.librgetter.gui.goals.add.AddCustomGoalScreen;
import dev.gxlg.librgetter.gui.goals.add.AddGoalScreen;
import dev.gxlg.librgetter.gui.goals.list.GoalListEntry;
import dev.gxlg.librgetter.gui.goals.list.GoalListScreen;
import dev.gxlg.librgetter.gui.goals.list.GoalSelectionList;
import dev.gxlg.librgetter.gui.goals.list.GoalSelectionList_1_20_3;
import dev.gxlg.librgetter.gui.goals.select.EnchantmentListEntry;
import dev.gxlg.librgetter.gui.goals.select.EnchantmentSelectionList;
import dev.gxlg.librgetter.gui.goals.select.EnchantmentSelectionList_1_20_3;
import dev.gxlg.librgetter.gui.goals.select.SelectEnchantmentScreen;
import dev.gxlg.librgetter.gui.widgets.list.CustomSelectionList;
import dev.gxlg.librgetter.gui.widgets.list.CustomSelectionListEntry;
import dev.gxlg.librgetter.gui.widgets.list.CustomSelectionList_1_20_3;
import dev.gxlg.librgetter.gui.widgets.unified.editbox.LegacyEditBox;
import dev.gxlg.librgetter.gui.widgets.unified.string.LegacyStringWidget;
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

        R.preload(
            ConfigScreen.clazz,
            AbstractAddGoalScreen.clazz,
            AddCustomGoalScreen.clazz,
            AddGoalScreen.clazz,
            GoalListEntry.clazz,
            GoalListScreen.clazz,
            GoalSelectionList.clazz,
            GoalSelectionList_1_20_3.clazz,
            EnchantmentListEntry.clazz,
            EnchantmentSelectionList.clazz,
            EnchantmentSelectionList_1_20_3.clazz,
            SelectEnchantmentScreen.clazz,
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
