package dev.gxlg.librgetter.services.types;

import dev.gxlg.librgetter.services.ServiceLoader;

public record Dependency<L extends ServiceLoader<L>, T>(L serviceLoader, Export<L, T> export) { }
