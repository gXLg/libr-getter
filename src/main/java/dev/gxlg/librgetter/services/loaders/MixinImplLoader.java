package dev.gxlg.librgetter.services.loaders;

import dev.gxlg.librgetter.compatibility.CompatibilityManager;
import dev.gxlg.librgetter.mixin.MixinImpl;
import dev.gxlg.librgetter.mixin.impl.ClientPacketListenerMixinImpl;
import dev.gxlg.librgetter.mixin.impl.MultiPlayerGameModeMixinImpl;
import dev.gxlg.librgetter.mixin.impl.PlayerMixinImpl;
import dev.gxlg.librgetter.savefiles.tradehalls.TradehallAutoSaver;
import dev.gxlg.librgetter.services.ServiceLoader;
import dev.gxlg.librgetter.worker.scheduling.controllers.SystemSchedulerController;
import dev.gxlg.librgetter.worker.state.StateView;

import java.util.function.Supplier;

public class MixinImplLoader extends ServiceLoader<MixinImplLoader> {
    private final Supplier<StateView> dependencyStateView;

    private final Supplier<SystemSchedulerController> dependencySystemSchedulerController;

    private final Supplier<CompatibilityManager> dependencyCompatibilityManager;

    private final Supplier<TradehallAutoSaver> dependencyTradehallAutoSaver;

    public MixinImplLoader(WorkerLoader workerLoader, CompatibilityLoader compatibilityLoader, TradehallUpdaterLoader tradehallUpdaterLoader) {
        dependencyStateView = initDependency(workerLoader, WorkerLoader.exportStateView);
        dependencySystemSchedulerController = initDependency(workerLoader, WorkerLoader.exportSystemSchedulerController);
        dependencyCompatibilityManager = initDependency(compatibilityLoader, CompatibilityLoader.exportCompatibilityManager);
        dependencyTradehallAutoSaver = initDependency(tradehallUpdaterLoader, TradehallUpdaterLoader.exportTradehallAutoSaver);
    }

    @Override
    public void init() {
        StateView stateView = dependencyStateView.get();
        SystemSchedulerController systemSchedulerController = dependencySystemSchedulerController.get();
        CompatibilityManager compatibilityManager = dependencyCompatibilityManager.get();
        TradehallAutoSaver tradehallAutoSaver = dependencyTradehallAutoSaver.get();

        MixinImpl.init(ClientPacketListenerMixinImpl.class, new ClientPacketListenerMixinImpl(stateView, systemSchedulerController, compatibilityManager, tradehallAutoSaver));
        MixinImpl.init(MultiPlayerGameModeMixinImpl.class, new MultiPlayerGameModeMixinImpl(stateView, tradehallAutoSaver));
        MixinImpl.init(PlayerMixinImpl.class, new PlayerMixinImpl(stateView));
    }
}
