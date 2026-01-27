package dev.gxlg.multiversion.gen.net.fabricmc.fabric.api.client.networking.v1;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class ClientPlayConnectionEvents$JoinWrapper extends R.RWrapper<ClientPlayConnectionEvents$JoinWrapper> {
    public static final R.RClass clazz = R.clz("net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents$Join");

    private int superCall = 0;

    protected ClientPlayConnectionEvents$JoinWrapper(Object instance) {
        super(instance);
    }

    public static ClientPlayConnectionEvents$JoinWrapper inst(Object instance) {
        return instance == null ? null : new ClientPlayConnectionEvents$JoinWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") ClientPlayConnectionEvents$JoinWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}