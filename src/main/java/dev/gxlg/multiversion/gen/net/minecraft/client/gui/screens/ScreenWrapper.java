package dev.gxlg.multiversion.gen.net.minecraft.client.gui.screens;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class ScreenWrapper extends R.RWrapper<ScreenWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_437/net.minecraft.client.gui.screens.Screen");

    private int superCall = 0;

    protected ScreenWrapper(Object instance) {
        super(instance);
    }

    public static ScreenWrapper inst(Object instance) {
        return instance == null ? null : new ScreenWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") ScreenWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}