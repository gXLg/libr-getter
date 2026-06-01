package dev.gxlg.librgetter.utils.adapters;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class SetAdapter {
    public static <S> Function<Object, Set<S>> wrapper(Function<Object, S> wrapperS, Function<S, Object> unwrapperS) {
        return object -> ((Set<?>) object).stream().map(wrapperS).collect(Collectors.toSet());
    }

    public static <S> Function<Set<S>, Object> unwrapper(Function<Object, S> wrapperS, Function<S, Object> unwrapperS) {
        return set -> set.stream().map(unwrapperS).collect(Collectors.toSet());
    }
}
