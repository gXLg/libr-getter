package dev.gxlg.multiversion.gen.net.fabricmc.fabric.api.client.event.lifecycle.v1;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class ClientTickEvents$EndTickWrapper extends R.RWrapper<ClientTickEvents$EndTickWrapper> {
    public static final R.RClass clazz = R.clz("net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents$EndTick");

    private int superCall = 0;

    protected ClientTickEvents$EndTickWrapper(Object instance) {
        super(instance);
    }

    public static ClientTickEvents$EndTickWrapper inst(Object instance) {
        return instance == null ? null : new ClientTickEvents$EndTickWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") ClientTickEvents$EndTickWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}