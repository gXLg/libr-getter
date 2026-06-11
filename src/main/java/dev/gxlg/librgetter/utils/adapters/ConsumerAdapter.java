package dev.gxlg.librgetter.utils.adapters;

import java.util.function.Consumer;
import java.util.function.Function;

@SuppressWarnings("unused")
public class ConsumerAdapter {
    @SuppressWarnings("unchecked")
    public static <S> Function<Object, Consumer<S>> wrapper(Function<Object, S> wrapperS, Function<S, Object> unwrapperS) {
        return object -> s -> ((Consumer<Object>) object).accept(unwrapperS.apply(s));
    }

    public static <S> Function<Consumer<S>, Object> unwrapper(Function<Object, S> wrapperS, Function<S, Object> unwrapperS) {
        return consumer -> (Consumer<S>) obj -> consumer.accept(wrapperS.apply(obj));
    }
}
