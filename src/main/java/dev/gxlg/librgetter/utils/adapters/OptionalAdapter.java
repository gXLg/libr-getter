package dev.gxlg.librgetter.utils.adapters;

import java.util.Optional;
import java.util.function.Function;

public class OptionalAdapter {
    public static <S> Function<Object, Optional<S>> wrapper(Function<Object, S> wrapperS) {
        return object -> ((Optional<?>) object).map(wrapperS);
    }

    public static <S> Function<Optional<S>, Object> unwrapper(Function<S, Object> unwrapperS) {
        return optional -> optional.map(unwrapperS);
    }
}
