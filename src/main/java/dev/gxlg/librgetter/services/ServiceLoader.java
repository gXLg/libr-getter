package dev.gxlg.librgetter.services;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class ServiceLoader<L extends ServiceLoader<L>> {
    private final List<Dependency<?, ?>> dependencies = new ArrayList<>();

    protected final <SL extends ServiceLoader<SL>, T> Supplier<T> initDependency(SL serviceLoader, Export<SL, T> export) {
        dependencies.add(new Dependency<>(serviceLoader, export));
        return () -> serviceLoader.get(export);
    }

    public final List<? extends ServiceLoader<?>> dependsOn() {
        return dependencies.stream().map(Dependency::serviceLoader).toList();
    }

    @SuppressWarnings("unchecked")
    public final <T> T get(Export<L, T> export) {
        return export.valueGetter().apply((L) this);
    }

    public abstract void init();
}
