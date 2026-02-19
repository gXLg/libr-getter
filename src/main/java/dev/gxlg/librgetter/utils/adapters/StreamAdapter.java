package dev.gxlg.librgetter.utils.adapters;

import java.util.function.Function;
import java.util.stream.Stream;

public class StreamAdapter {
    public static <S> Function<Object, Stream<S>> wrapper(Function<Object, S> wrapperS) {
        return obj -> ((Stream<?>) obj).map(wrapperS);
    }

    public static <S> Function<Stream<S>, Object> unwrapper(Function<S, Object> unwrapperS) {
        return stream -> stream.map(unwrapperS);
    }
}
