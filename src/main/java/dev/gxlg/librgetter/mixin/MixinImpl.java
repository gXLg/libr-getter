package dev.gxlg.librgetter.mixin;

import dev.gxlg.versiont.api.types.Wrapper;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@SuppressWarnings("unchecked")
public class MixinImpl {
    private static final Map<Class<?>, Object> instances = new HashMap<>();

    public static <M> void init(Class<M> type, M instance) {
        instances.put(type, instance);
    }

    private static <M> void execute(Class<M> type, Consumer<M> consumer) {
        if (!instances.containsKey(type)) {
            return;
        }
        consumer.accept((M) instances.get(type));
    }

    public static <M> void mixin(Class<M> type, Consumer<M> function) {
        execute(type, function);
    }

    public static <M> void mixinVoid(Class<M> type, CallbackInfo info, Function<M, Optional<Object>> function) {
        execute(type, i -> function.apply(i).ifPresent(r -> info.cancel()));
    }

    public static <M, T> void mixinReturn(Class<M> type, CallbackInfoReturnable<T> info, Function<M, Optional<T>> function) {
        execute(type, i -> function.apply(i).ifPresent(info::setReturnValue));
    }

    public static <M> void mixinReturnWrapped(Class<M> type, CallbackInfoReturnable<Object> info, Function<M, Optional<? extends Wrapper<?>>> function) {
        execute(type, i -> function.apply(i).ifPresent(r -> info.setReturnValue(r.unwrap())));
    }
}
