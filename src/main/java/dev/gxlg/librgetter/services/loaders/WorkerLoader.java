package dev.gxlg.librgetter.services.loaders;

import dev.gxlg.librgetter.compatibility.CompatibilityManager;
import dev.gxlg.librgetter.services.Export;
import dev.gxlg.librgetter.services.ServiceLoader;
import dev.gxlg.librgetter.utils.config.ConfigManager;
import dev.gxlg.librgetter.worker.Worker;
import dev.gxlg.librgetter.worker.scheduling.controllers.SystemSchedulerController;
import dev.gxlg.librgetter.worker.scheduling.controllers.UserSchedulerController;
import dev.gxlg.librgetter.worker.state.StateView;

import java.util.function.Supplier;

public class WorkerLoader extends ServiceLoader<WorkerLoader> {
    public static final Export<WorkerLoader, StateView> exportStateView = new Export<>(w -> w.worker.getStateView());

    public static final Export<WorkerLoader, UserSchedulerController> exportUserSchedulerController = new Export<>(w -> w.worker.getUserSchedulerController());

    public static final Export<WorkerLoader, SystemSchedulerController> exportSystemSchedulerController = new Export<>(w -> w.worker.getSystemSchedulerController());

    private final Supplier<ConfigManager> dependencyConfigManager;

    private final Supplier<CompatibilityManager> dependencyCompatibilityManager;

    private Worker worker;

    public WorkerLoader(ConfigLoader configLoader, CompatibilityLoader compatibilityLoader) {
        dependencyConfigManager = initDependency(configLoader, ConfigLoader.exportConfigManager);
        dependencyCompatibilityManager = initDependency(compatibilityLoader, CompatibilityLoader.exportCompatibilityManager);
    }

    @Override
    public void init() {
        worker = new Worker(dependencyConfigManager.get(), dependencyCompatibilityManager.get());
    }
}
