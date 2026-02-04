package dev.gxlg.multiversion.adapters.java.util;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SetAdapter {
    public static <S> Function<Object, Set<S>> wrapper(Function<Object, S> wrapperS) {
        return object -> ((Set<?>) object).stream().map(wrapperS).collect(Collectors.toSet());
    }

    public static <S> Function<Set<S>, Object> unwrapper(Function<S, Object> unwrapperS) {
        return set -> set.stream().map(unwrapperS).collect(Collectors.toSet());
    }
}
