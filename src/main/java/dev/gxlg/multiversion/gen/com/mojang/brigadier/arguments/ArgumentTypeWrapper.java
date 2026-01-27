package dev.gxlg.multiversion.gen.com.mojang.brigadier.arguments;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class ArgumentTypeWrapper extends R.RWrapper<ArgumentTypeWrapper> {
    public static final R.RClass clazz = R.clz("com.mojang.brigadier.arguments.ArgumentType");

    private int superCall = 0;

    protected ArgumentTypeWrapper(Object instance) {
        super(instance);
    }

    public static ArgumentTypeWrapper inst(Object instance) {
        return instance == null ? null : new ArgumentTypeWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") ArgumentTypeWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}