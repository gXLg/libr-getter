package dev.gxlg.librgetter.services.loaders;

import dev.gxlg.librgetter.commands.CommandsManager;
import dev.gxlg.librgetter.controller.SharedController;
import dev.gxlg.librgetter.savefiles.config.ConfigManager;
import dev.gxlg.librgetter.savefiles.goals.GoalListManager;
import dev.gxlg.librgetter.services.ServiceLoader;

import java.util.function.Supplier;

public class CommandsLoader extends ServiceLoader<CommandsLoader> {
    private final Supplier<ConfigManager> dependencyConfigManager;

    private final Supplier<GoalListManager> dependencyGoalListManager;

    private final Supplier<SharedController> dependencySharedController;

    public CommandsLoader(SaveFileLoader saveFileLoader, SharedControllerLoader sharedControllerLoader) {
        dependencyConfigManager = initDependency(saveFileLoader, SaveFileLoader.exportConfigManager);
        dependencyGoalListManager = initDependency(saveFileLoader, SaveFileLoader.exportGoalListManager);
        dependencySharedController = initDependency(sharedControllerLoader, SharedControllerLoader.exportSharedController);
    }

    @Override
    public void init() {
        ConfigManager configManager = dependencyConfigManager.get();
        GoalListManager goalListManager = dependencyGoalListManager.get();
        SharedController sharedController = dependencySharedController.get();

        CommandsManager commandsManager = new CommandsManager(configManager, goalListManager, sharedController);
        commandsManager.register();
    }
}
