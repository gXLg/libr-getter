package dev.gxlg.librgetter.services.loaders;

import dev.gxlg.librgetter.compatibility.CompatibilityManager;
import dev.gxlg.librgetter.savefiles.config.ConfigManager;
import dev.gxlg.librgetter.savefiles.goals.GoalListManager;
import dev.gxlg.librgetter.services.ServiceLoader;
import dev.gxlg.librgetter.services.types.Export;
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

    private final Supplier<GoalListManager> dependencyGoalListManager;

    private final Supplier<CompatibilityManager> dependencyCompatibilityManager;

    private Worker worker;

    public WorkerLoader(SaveFileLoader saveFileLoader, CompatibilityLoader compatibilityLoader) {
        dependencyConfigManager = initDependency(saveFileLoader, SaveFileLoader.exportConfigManager);
        dependencyGoalListManager = initDependency(saveFileLoader, SaveFileLoader.exportGoalListManager);
        dependencyCompatibilityManager = initDependency(compatibilityLoader, CompatibilityLoader.exportCompatibilityManager);
    }

    @Override
    public void init() {
        ConfigManager configManager = dependencyConfigManager.get();
        GoalListManager goalListManager = dependencyGoalListManager.get();
        CompatibilityManager compatibilityManager = dependencyCompatibilityManager.get();

        worker = new Worker(configManager, goalListManager, compatibilityManager);
    }
}
