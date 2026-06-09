package dev.gxlg.librgetter.utils.adapters;

import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class SupplierAdapter {
    public static <S> Function<Object, Supplier<S>> wrapper(Function<Object, S> wrapperS, Function<S, Object> unwrapperS) {
        return object -> () -> wrapperS.apply(((Supplier<?>) object).get());
    }

    public static <S> Function<Supplier<S>, Object> unwrapper(Function<Object, S> wrapperS, Function<S, Object> unwrapperS) {
        return supplier -> (Supplier<?>) () -> unwrapperS.apply(supplier.get());
    }
}
