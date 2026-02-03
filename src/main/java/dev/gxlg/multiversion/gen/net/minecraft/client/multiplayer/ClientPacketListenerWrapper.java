package dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class ClientPacketListenerWrapper extends dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.ClientCommonPacketListenerImplWrapper {
    public static final R.RClass clazz = R.clz("net.minecraft.class_634/net.minecraft.client.multiplayer.ClientPacketListener");

    private int superCall = 0;

    protected ClientPacketListenerWrapper(Object instance) {
        super(instance);
    }

    public static ClientPacketListenerWrapper inst(Object instance) {
        return instance == null ? null : new ClientPacketListenerWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") ClientPacketListenerWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.ClientCommonPacketListenerImplWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}