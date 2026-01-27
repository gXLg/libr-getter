package dev.gxlg.multiversion.gen.net.minecraft.network.protocol.game;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class ServerboundContainerClosePacketWrapper extends dev.gxlg.multiversion.gen.net.minecraft.network.protocol.PacketWrapper {
    public static final R.RClass clazz = R.clz("net.minecraft.class_2815/net.minecraft.network.protocol.game.ServerboundContainerClosePacket");

    private int superCall = 0;

    public ServerboundContainerClosePacketWrapper(int containerId) {
        this(clazz.constr(int.class).newInst(containerId).self());
    }

    protected ServerboundContainerClosePacketWrapper(Object instance) {
        super(instance);
    }

    public static ServerboundContainerClosePacketWrapper inst(Object instance) {
        return instance == null ? null : new ServerboundContainerClosePacketWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") ServerboundContainerClosePacketWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return dev.gxlg.multiversion.gen.net.minecraft.network.protocol.PacketWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}