package dev.gxlg.multiversion.adapters.java.lang;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class IterableAdapter {
    public static <S> Function<Object, Iterable<S>> wrapper(Function<Object, S> wrapperS) {
        return obj -> {
            Iterable<?> x = ((Iterable<?>) obj);
            List<S> list = new ArrayList<>();
            for (Object o : x) {
                list.add(wrapperS.apply(o));
            }
            return list;
        };
    }

    public static <S> Function<Iterable<S>, Object> unwrapper(Function<S, Object> unwrapperS) {
        return iter -> {
            List<Object> list = new ArrayList<>();
            for (S s : iter) {
                list.add(unwrapperS.apply(s));
            }
            return list;
        };
    }
}
