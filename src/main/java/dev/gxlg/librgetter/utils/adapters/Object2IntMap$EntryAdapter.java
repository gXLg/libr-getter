package dev.gxlg.librgetter.utils.adapters;

import it.unimi.dsi.fastutil.objects.Object2IntMap;

import java.util.function.Function;

public class Object2IntMap$EntryAdapter {
    public static <S> Function<Object, Object2IntMap.Entry<S>> wrapper(Function<Object, S> wrapperS) {
        return object -> {
            Object2IntMap.Entry<?> entry = (Object2IntMap.Entry<?>) object;
            return Object2IntMap.entry(wrapperS.apply(entry.getKey()), entry.getIntValue());
        };
    }

    public static <S> Function<Object2IntMap.Entry<S>, Object> unwrapper(Function<S, Object> unwrapperS) {
        return entry -> Object2IntMap.entry(unwrapperS.apply(entry.getKey()), entry.getIntValue());
    }
}
