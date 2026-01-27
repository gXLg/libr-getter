package dev.gxlg.multiversion.gen.net.minecraft.network.protocol.common.custom;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class CustomPacketPayloadWrapper extends R.RWrapper<CustomPacketPayloadWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_8710/net.minecraft.network.protocol.common.custom.CustomPacketPayload");

    private int superCall = 0;

    protected CustomPacketPayloadWrapper(Object instance) {
        super(instance);
    }

    public static CustomPacketPayloadWrapper inst(Object instance) {
        return instance == null ? null : new CustomPacketPayloadWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") CustomPacketPayloadWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}