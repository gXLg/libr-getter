package dev.gxlg.librgetter.utils.adapters;

import java.util.Optional;
import java.util.function.Function;

@SuppressWarnings("unused")
public class OptionalAdapter {
    public static <S> Function<Object, Optional<S>> wrapper(Function<Object, S> wrapperS, Function<S, Object> unwrapperS) {
        return object -> ((Optional<?>) object).map(wrapperS);
    }

    public static <S> Function<Optional<S>, Object> unwrapper(Function<Object, S> wrapperS, Function<S, Object> unwrapperS) {
        return optional -> optional.map(unwrapperS);
    }
}
