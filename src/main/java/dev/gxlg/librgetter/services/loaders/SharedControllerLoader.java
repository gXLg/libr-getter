package dev.gxlg.librgetter.services.loaders;

import dev.gxlg.librgetter.controller.SharedController;
import dev.gxlg.librgetter.services.Export;
import dev.gxlg.librgetter.services.ServiceLoader;
import dev.gxlg.librgetter.worker.scheduling.controllers.UserSchedulerController;
import dev.gxlg.librgetter.worker.state.StateView;

import java.util.function.Supplier;

public class SharedControllerLoader extends ServiceLoader<SharedControllerLoader> {
    public static final Export<SharedControllerLoader, SharedController> exportSharedController = new Export<>(s -> s.sharedController);

    private final Supplier<StateView> dependencyStateView;

    private final Supplier<UserSchedulerController> dependencyController;

    private SharedController sharedController;

    public SharedControllerLoader(WorkerLoader workerLoader) {
        dependencyStateView = initDependency(workerLoader, WorkerLoader.exportStateView);
        dependencyController = initDependency(workerLoader, WorkerLoader.exportUserSchedulerController);
    }

    @Override
    public void init() {
        sharedController = new SharedController(dependencyStateView.get(), dependencyController.get());
    }
}
