package dev.gxlg.multiversion.gen.net.minecraft.sounds;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class SoundSourceWrapper extends R.RWrapper<SoundSourceWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_3419/net.minecraft.sounds.SoundSource");

    private int superCall = 0;

    protected SoundSourceWrapper(Object instance) {
        super(instance);
    }

    public static SoundSourceWrapper inst(Object instance) {
        return instance == null ? null : new SoundSourceWrapper(instance);
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.sounds.SoundSourceWrapper NEUTRAL() {
        return dev.gxlg.multiversion.gen.net.minecraft.sounds.SoundSourceWrapper.inst(clazz.fld("field_15254/NEUTRAL", dev.gxlg.multiversion.gen.net.minecraft.sounds.SoundSourceWrapper.clazz.self()).get());
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") SoundSourceWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}