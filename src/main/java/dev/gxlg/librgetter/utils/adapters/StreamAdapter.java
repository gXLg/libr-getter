package dev.gxlg.librgetter.utils.adapters;

import java.util.function.Function;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class StreamAdapter {
    public static <S> Function<Object, Stream<S>> wrapper(Function<Object, S> wrapperS, Function<S, Object> unwrapperS) {
        return object -> ((Stream<?>) object).map(wrapperS);
    }

    public static <S> Function<Stream<S>, Object> unwrapper(Function<Object, S> wrapperS, Function<S, Object> unwrapperS) {
        return stream -> stream.map(unwrapperS);
    }
}
