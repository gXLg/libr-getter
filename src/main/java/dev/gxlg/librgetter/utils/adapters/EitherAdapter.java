package dev.gxlg.librgetter.utils.adapters;

import com.mojang.datafixers.util.Either;

import java.util.function.Function;

@SuppressWarnings("unused")
public class EitherAdapter {
    public static <S, R> Function<Object, Either<S, R>> wrapper(Function<Object, S> wrapperS, Function<S, Object> unwrapperS, Function<Object, R> wrapperR, Function<R, Object> unwrapperR) {
        return object -> ((Either<?, ?>) object).mapBoth(wrapperS, wrapperR);
    }

    public static <S, R> Function<Either<S, R>, Object> unwrapper(Function<Object, S> wrapperS, Function<S, Object> unwrapperS, Function<Object, R> wrapperR, Function<R, Object> unwrapperR) {
        return either -> either.mapBoth(unwrapperS, unwrapperR);
    }
}
