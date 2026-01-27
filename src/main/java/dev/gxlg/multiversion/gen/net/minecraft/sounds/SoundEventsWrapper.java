package dev.gxlg.multiversion.gen.net.minecraft.sounds;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class SoundEventsWrapper extends R.RWrapper<SoundEventsWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_3417/net.minecraft.sounds.SoundEvents");

    private int superCall = 0;

    protected SoundEventsWrapper(Object instance) {
        super(instance);
    }

    public static SoundEventsWrapper inst(Object instance) {
        return instance == null ? null : new SoundEventsWrapper(instance);
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.sounds.SoundEventWrapper PLAYER_LEVELUP() {
        return dev.gxlg.multiversion.gen.net.minecraft.sounds.SoundEventWrapper.inst(clazz.fld("field_14709/PLAYER_LEVELUP", dev.gxlg.multiversion.gen.net.minecraft.sounds.SoundEventWrapper.clazz).get());
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") SoundEventsWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}