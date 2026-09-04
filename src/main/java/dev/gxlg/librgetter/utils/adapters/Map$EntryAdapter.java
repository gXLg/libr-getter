package dev.gxlg.librgetter.utils.adapters;

import java.util.Map;
import java.util.function.Function;

@SuppressWarnings("unused")
public class Map$EntryAdapter {
    public static <S, T> Function<Object, Map.Entry<S, T>> wrapper(Function<Object, S> wrapperS, Function<S, Object> unwrapperS, Function<Object, T> wrapperT, Function<T, Object> unwrapperT) {
        return object -> {
            Map.Entry<?, ?> entry = (Map.Entry<?, ?>) object;
            return Map.entry(wrapperS.apply(entry.getKey()), wrapperT.apply(entry.getValue()));
        };
    }

    public static <S, T> Function<Map.Entry<S, T>, Object> unwrapper(Function<Object, S> wrapperS, Function<S, Object> unwrapperS, Function<Object, T> wrapperT, Function<T, Object> unwrapperT) {
        return entry -> Map.entry(unwrapperS.apply(entry.getKey()), unwrapperT.apply(entry.getValue()));
    }
}
