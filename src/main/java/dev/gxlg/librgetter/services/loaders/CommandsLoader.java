package dev.gxlg.librgetter.services.loaders;

import dev.gxlg.librgetter.commands.CommandsManager;
import dev.gxlg.librgetter.config.ConfigManager;
import dev.gxlg.librgetter.controller.SharedController;
import dev.gxlg.librgetter.services.ServiceLoader;

import java.util.function.Supplier;

public class CommandsLoader extends ServiceLoader<CommandsLoader> {
    private final Supplier<ConfigManager> dependencyConfigManager;

    private final Supplier<SharedController> dependencySharedController;

    public CommandsLoader(ConfigLoader configLoader, SharedControllerLoader sharedControllerLoader) {
        dependencyConfigManager = initDependency(configLoader, ConfigLoader.exportConfigManager);
        dependencySharedController = initDependency(sharedControllerLoader, SharedControllerLoader.exportSharedController);
    }

    @Override
    public void init() {
        CommandsManager commandsManager = new CommandsManager(dependencyConfigManager.get(), dependencySharedController.get());
        commandsManager.register();
    }
}
