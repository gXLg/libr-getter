package dev.gxlg.librgetter.utils.adapters;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@SuppressWarnings("unused")
public class IterableAdapter {
    public static <S> Function<Object, Iterable<S>> wrapper(Function<Object, S> wrapperS, Function<S, Object> unwrapperS) {
        return object -> {
            Iterable<?> objectIterable = ((Iterable<?>) object);
            List<S> list = new ArrayList<>();
            for (Object objectElement : objectIterable) {
                list.add(wrapperS.apply(objectElement));
            }
            return list;
        };
    }

    public static <S> Function<Iterable<S>, Object> unwrapper(Function<Object, S> wrapperS, Function<S, Object> unwrapperS) {
        return iterable -> {
            List<Object> list = new ArrayList<>();
            for (S sElement : iterable) {
                list.add(unwrapperS.apply(sElement));
            }
            return list;
        };
    }
}
