package dev.gxlg.multiversion.gen.net.minecraft.network.protocol.game;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class ServerboundSelectTradePacketWrapper extends dev.gxlg.multiversion.gen.net.minecraft.network.protocol.PacketWrapper {
    public static final R.RClass clazz = R.clz("net.minecraft.class_2863/net.minecraft.network.protocol.game.ServerboundSelectTradePacket");

    private int superCall = 0;

    public ServerboundSelectTradePacketWrapper(int index) {
        this(clazz.constr(int.class).newInst(index).self());
    }

    protected ServerboundSelectTradePacketWrapper(Object instance) {
        super(instance);
    }

    public static ServerboundSelectTradePacketWrapper inst(Object instance) {
        return instance == null ? null : new ServerboundSelectTradePacketWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") ServerboundSelectTradePacketWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return dev.gxlg.multiversion.gen.net.minecraft.network.protocol.PacketWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}