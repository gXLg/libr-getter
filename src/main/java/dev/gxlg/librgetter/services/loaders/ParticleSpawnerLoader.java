package dev.gxlg.librgetter.services.loaders;

import dev.gxlg.librgetter.particles.WorkstationParticleSpawner;
import dev.gxlg.librgetter.services.ServiceLoader;
import dev.gxlg.librgetter.worker.state.StateView;

import java.util.function.Supplier;

public class ParticleSpawnerLoader extends ServiceLoader<ParticleSpawnerLoader> {
    private final Supplier<StateView> dependencyStateView;

    public ParticleSpawnerLoader(WorkerLoader workerLoader) {
        this.dependencyStateView = initDependency(workerLoader, WorkerLoader.exportStateView);
    }

    @Override
    public void init() {
        StateView stateView = dependencyStateView.get();

        WorkstationParticleSpawner spawner = new WorkstationParticleSpawner(stateView);
        spawner.start();
    }
}
