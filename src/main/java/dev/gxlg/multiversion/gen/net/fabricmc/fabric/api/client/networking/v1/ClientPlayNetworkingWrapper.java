package dev.gxlg.multiversion.gen.net.fabricmc.fabric.api.client.networking.v1;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class ClientPlayNetworkingWrapper extends R.RWrapper<ClientPlayNetworkingWrapper> {
    public static final R.RClass clazz = R.clz("net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking");

    private int superCall = 0;

    protected ClientPlayNetworkingWrapper(Object instance) {
        super(instance);
    }

    public static ClientPlayNetworkingWrapper inst(Object instance) {
        return instance == null ? null : new ClientPlayNetworkingWrapper(instance);
    }

    public static void send(dev.gxlg.multiversion.gen.net.minecraft.network.protocol.common.custom.CustomPacketPayloadWrapper customPayload){
        clazz.mthd("send", void.class, dev.gxlg.multiversion.gen.net.minecraft.network.protocol.common.custom.CustomPacketPayloadWrapper.clazz).invk(customPayload.unwrap());
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") ClientPlayNetworkingWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}