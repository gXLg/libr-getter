package dev.gxlg.librgetter.services.loaders;

import dev.gxlg.librgetter.compatibility.CompatibilityManager;
import dev.gxlg.librgetter.mixin.MixinImpl;
import dev.gxlg.librgetter.mixin.impl.ClientPacketListenerMixinImpl;
import dev.gxlg.librgetter.mixin.impl.MultiPlayerGameModeMixinImpl;
import dev.gxlg.librgetter.services.ServiceLoader;
import dev.gxlg.librgetter.worker.scheduling.controllers.SystemSchedulerController;
import dev.gxlg.librgetter.worker.state.StateView;

import java.util.function.Supplier;

public class MixinImplLoader extends ServiceLoader<MixinImplLoader> {
    private final Supplier<StateView> dependencyStateView;

    private final Supplier<SystemSchedulerController> dependencySystemSchedulerController;

    private final Supplier<CompatibilityManager> dependencyCompatibilityManager;

    public MixinImplLoader(WorkerLoader workerLoader, CompatibilityLoader compatibilityLoader) {
        dependencyStateView = initDependency(workerLoader, WorkerLoader.exportStateView);
        dependencySystemSchedulerController = initDependency(workerLoader, WorkerLoader.exportSystemSchedulerController);
        dependencyCompatibilityManager = initDependency(compatibilityLoader, CompatibilityLoader.exportCompatibilityManager);
    }

    @Override
    public void init() {
        StateView stateView = dependencyStateView.get();
        SystemSchedulerController systemSchedulerController = dependencySystemSchedulerController.get();
        CompatibilityManager compatibilityManager = dependencyCompatibilityManager.get();

        MixinImpl.init(ClientPacketListenerMixinImpl.class, new ClientPacketListenerMixinImpl(stateView, systemSchedulerController, compatibilityManager));
        MixinImpl.init(MultiPlayerGameModeMixinImpl.class, new MultiPlayerGameModeMixinImpl(stateView));
    }
}
