package dev.gxlg.multiversion.gen.net.minecraft.util;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class MthWrapper extends R.RWrapper<MthWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_3532/net.minecraft.util.Mth");

    private int superCall = 0;

    protected MthWrapper(Object instance) {
        super(instance);
    }

    public static MthWrapper inst(Object instance) {
        return instance == null ? null : new MthWrapper(instance);
    }

    public static float wrapDegrees(float angle){
        return (float) clazz.mthd("method_15393/wrapDegrees", float.class, float.class).invk(angle);
    }

    public static double atan2(double y, double x){
        return (double) clazz.mthd("method_15349/atan2", double.class, double.class, double.class).invk(y, x);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") MthWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}