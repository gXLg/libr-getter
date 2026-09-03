package dev.gxlg.librgetter.services.loaders;

import dev.gxlg.librgetter.particles.WorkstationParticleSpawner;
import dev.gxlg.librgetter.savefiles.config.ConfigManager;
import dev.gxlg.librgetter.services.ServiceLoader;
import dev.gxlg.librgetter.worker.state.StateView;

import java.util.function.Supplier;

public class ParticleSpawnerLoader extends ServiceLoader<ParticleSpawnerLoader> {
    private final Supplier<ConfigManager> dependencyConfigManager;

    private final Supplier<StateView> dependencyStateView;

    public ParticleSpawnerLoader(SaveFileLoader saveFileLoader, WorkerLoader workerLoader) {
        this.dependencyConfigManager = initDependency(saveFileLoader, SaveFileLoader.exportConfigManager);
        this.dependencyStateView = initDependency(workerLoader, WorkerLoader.exportStateView);
    }

    @Override
    public void init() {
        ConfigManager configManager = dependencyConfigManager.get();
        StateView stateView = dependencyStateView.get();

        WorkstationParticleSpawner spawner = new WorkstationParticleSpawner(configManager, stateView);
        spawner.start();
    }
}
