package dev.gxlg.multiversion.gen.net.fabricmc.fabric.api.client.command.v2;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class ClientCommandRegistrationCallbackWrapper extends R.RWrapper<ClientCommandRegistrationCallbackWrapper> {
    public static final R.RClass clazz = R.clz("net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback");

    private int superCall = 0;

    protected ClientCommandRegistrationCallbackWrapper(Object instance) {
        super(instance);
    }

    public static ClientCommandRegistrationCallbackWrapper inst(Object instance) {
        return instance == null ? null : new ClientCommandRegistrationCallbackWrapper(instance);
    }

    public static dev.gxlg.multiversion.gen.net.fabricmc.fabric.api.event.EventWrapper EVENT() {
        return dev.gxlg.multiversion.gen.net.fabricmc.fabric.api.event.EventWrapper.inst(clazz.fld("EVENT", dev.gxlg.multiversion.gen.net.fabricmc.fabric.api.event.EventWrapper.clazz.self()).get());
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") ClientCommandRegistrationCallbackWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}