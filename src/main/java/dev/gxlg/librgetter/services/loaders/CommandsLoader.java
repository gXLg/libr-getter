package dev.gxlg.librgetter.services.loaders;

import dev.gxlg.librgetter.commands.CommandsManager;
import dev.gxlg.librgetter.controller.SharedController;
import dev.gxlg.librgetter.savefiles.config.ConfigManager;
import dev.gxlg.librgetter.savefiles.goals.GoalListAccessor;
import dev.gxlg.librgetter.services.ServiceLoader;

import java.util.function.Supplier;

public class CommandsLoader extends ServiceLoader<CommandsLoader> {
    private final Supplier<ConfigManager> dependencyConfigManager;

    private final Supplier<GoalListAccessor> dependencyGoalListAccessor;

    private final Supplier<SharedController> dependencySharedController;

    public CommandsLoader(SaveFileLoader saveFileLoader, SharedControllerLoader sharedControllerLoader) {
        dependencyConfigManager = initDependency(saveFileLoader, SaveFileLoader.exportConfigManager);
        dependencyGoalListAccessor = initDependency(saveFileLoader, SaveFileLoader.exportGoalListAccessor);
        dependencySharedController = initDependency(sharedControllerLoader, SharedControllerLoader.exportSharedController);
    }

    @Override
    public void init() {
        ConfigManager configManager = dependencyConfigManager.get();
        GoalListAccessor goalListAccessor = dependencyGoalListAccessor.get();
        SharedController sharedController = dependencySharedController.get();

        CommandsManager commandsManager = new CommandsManager(configManager, goalListAccessor, sharedController);
        commandsManager.register();
    }
}
