package dev.gxlg.librgetter.services;

import java.util.function.Function;

public record Export<L extends ServiceLoader<L>, T>(Function<L, T> valueGetter) { }
