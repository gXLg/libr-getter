package dev.gxlg.multiversion.gen.net.minecraft.sounds;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class SoundEventWrapper extends R.RWrapper<SoundEventWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_3414/net.minecraft.sounds.SoundEvent");

    private int superCall = 0;

    protected SoundEventWrapper(Object instance) {
        super(instance);
    }

    public static SoundEventWrapper inst(Object instance) {
        return instance == null ? null : new SoundEventWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") SoundEventWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}