package dev.gxlg.multiversion.gen.com.mojang.brigadier.builder;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class LiteralArgumentBuilderWrapper extends dev.gxlg.multiversion.gen.com.mojang.brigadier.builder.ArgumentBuilderWrapper {
    public static final R.RClass clazz = R.clz("com.mojang.brigadier.builder.LiteralArgumentBuilder");

    private int superCall = 0;

    protected LiteralArgumentBuilderWrapper(Object instance) {
        super(instance);
    }

    public static LiteralArgumentBuilderWrapper inst(Object instance) {
        return instance == null ? null : new LiteralArgumentBuilderWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") LiteralArgumentBuilderWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return dev.gxlg.multiversion.gen.com.mojang.brigadier.builder.ArgumentBuilderWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}