package dev.gxlg.librgetter.services.loaders;

import dev.gxlg.librgetter.commands.CommandsManager;
import dev.gxlg.librgetter.services.ServiceLoader;
import dev.gxlg.librgetter.utils.config.ConfigManager;
import dev.gxlg.librgetter.worker.scheduling.controllers.UserSchedulerController;
import dev.gxlg.librgetter.worker.state.StateView;

import java.util.function.Supplier;

public class CommandsLoader extends ServiceLoader<CommandsLoader> {
    private final Supplier<ConfigManager> dependencyConfigManager;

    private final Supplier<UserSchedulerController> dependencyUserSchedulerController;

    private final Supplier<StateView> dependencyStateView;

    public CommandsLoader(ConfigLoader configLoader, WorkerLoader workerLoader) {
        dependencyConfigManager = initDependency(configLoader, ConfigLoader.exportConfigManager);
        dependencyUserSchedulerController = initDependency(workerLoader, WorkerLoader.exportUserSchedulerController);
        dependencyStateView = initDependency(workerLoader, WorkerLoader.exportStateView);
    }

    @Override
    public void init() {
        CommandsManager commandsManager = new CommandsManager(dependencyConfigManager.get(), dependencyUserSchedulerController.get(), dependencyStateView.get());
        commandsManager.register();
    }
}
