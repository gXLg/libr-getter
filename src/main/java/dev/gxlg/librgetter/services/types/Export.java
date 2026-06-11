package dev.gxlg.librgetter.services.types;

import dev.gxlg.librgetter.services.ServiceLoader;

import java.util.function.Function;

public record Export<L extends ServiceLoader<L>, T>(Function<L, T> valueGetter) { }
