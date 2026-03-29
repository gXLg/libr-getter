package dev.gxlg.librgetter.utils.adapters;

import com.mojang.datafixers.util.Either;

import java.util.function.Function;

public class EitherAdapter {
    public static <S, R> Function<Object, Either<S, R>> wrapper(Function<Object, S> wrapperS, Function<Object, R> wrapperR) {
        return object -> ((Either<?, ?>) object).mapBoth(wrapperS, wrapperR);
    }

    public static <S, R> Function<Either<S, R>, Object> unwrapper(Function<S, Object> unwrapperS, Function<R, Object> unwrapperR) {
        return either -> either.mapBoth(unwrapperS, unwrapperR);
    }
}
