package dev.gxlg.librgetter.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServiceLoaderManager {
    private final Set<ServiceLoader<?>> initServiceLoaders = new HashSet<>();

    private final List<ServiceLoader<?>> registeredServiceLoaders = new ArrayList<>();

    private boolean initialized = false;

    public void registerServiceLoader(ServiceLoader<?> serviceLoader) {
        if (initialized) {
            throw new IllegalStateException("Cannot register service-loaders after initialization");
        }
        registeredServiceLoaders.add(serviceLoader);
    }

    public void init() {
        if (initialized) {
            throw new IllegalStateException("Service-loader manager is already initialized");
        }
        for (ServiceLoader<?> service : registeredServiceLoaders) {
            initServiceLoader(service);
        }
        initialized = true;
    }

    private void initServiceLoader(ServiceLoader<?> serviceLoader) {
        if (initServiceLoaders.contains(serviceLoader)) {
            return;
        }
        for (ServiceLoader<?> loaderDependency : serviceLoader.dependsOn()) {
            initServiceLoader(loaderDependency);
        }
        serviceLoader.init();
        initServiceLoaders.add(serviceLoader);
    }
}
