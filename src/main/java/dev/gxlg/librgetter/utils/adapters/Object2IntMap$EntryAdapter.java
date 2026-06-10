package dev.gxlg.librgetter.utils.adapters;

import it.unimi.dsi.fastutil.objects.AbstractObject2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;

import java.util.function.Function;

@SuppressWarnings("unused")
public class Object2IntMap$EntryAdapter {
    public static <S> Function<Object, Object2IntMap.Entry<S>> wrapper(Function<Object, S> wrapperS, Function<S, Object> unwrapperS) {
        return object -> {
            Object2IntMap.Entry<?> entry = (Object2IntMap.Entry<?>) object;
            return new AbstractObject2IntMap.BasicEntry<>(wrapperS.apply(entry.getKey()), entry.getIntValue());
        };
    }

    public static <S> Function<Object2IntMap.Entry<S>, Object> unwrapper(Function<Object, S> wrapperS, Function<S, Object> unwrapperS) {
        return entry -> Object2IntMap.entry(unwrapperS.apply(entry.getKey()), entry.getIntValue());
    }
}
