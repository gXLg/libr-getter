package dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class ClientCommonPacketListenerImplWrapper extends R.RWrapper<ClientCommonPacketListenerImplWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_8673/net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl");

    private int superCall = 0;

    protected ClientCommonPacketListenerImplWrapper(Object instance) {
        super(instance);
    }

    public void send(dev.gxlg.multiversion.gen.net.minecraft.network.protocol.PacketWrapper packet){
        clazz.inst(this.instance).mthd("method_2883/send", void.class, dev.gxlg.multiversion.gen.net.minecraft.network.protocol.PacketWrapper.clazz.self()).invk(packet.unwrap());
    }

    public static ClientCommonPacketListenerImplWrapper inst(Object instance) {
        return instance == null ? null : new ClientCommonPacketListenerImplWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") ClientCommonPacketListenerImplWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}