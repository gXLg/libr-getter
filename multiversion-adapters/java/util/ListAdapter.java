package dev.gxlg.multiversion.adapters.java.util;

import java.util.List;
import java.util.function.Function;

public class ListAdapter {
    public static <S> Function<Object, List<S>> wrapper(Function<Object, S> wrapperT) {
        return object -> ((List<?>) object).stream().map(wrapperT).toList();
    }

    public static <S> Function<List<S>, Object> unwrapper(Function<S, Object> unwrapperT) {
        return list -> list.stream().map(unwrapperT).toList();
    }
}
