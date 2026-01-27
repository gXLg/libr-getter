package dev.gxlg.multiversion.gen.net.minecraft.network.protocol.game;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class ServerboundSetCarriedItemPacketWrapper extends dev.gxlg.multiversion.gen.net.minecraft.network.protocol.PacketWrapper {
    public static final R.RClass clazz = R.clz("net.minecraft.class_2868/net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket");

    private int superCall = 0;

    public ServerboundSetCarriedItemPacketWrapper(int slotId) {
        this(clazz.constr(int.class).newInst(slotId).self());
    }

    protected ServerboundSetCarriedItemPacketWrapper(Object instance) {
        super(instance);
    }

    public static ServerboundSetCarriedItemPacketWrapper inst(Object instance) {
        return instance == null ? null : new ServerboundSetCarriedItemPacketWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") ServerboundSetCarriedItemPacketWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return dev.gxlg.multiversion.gen.net.minecraft.network.protocol.PacketWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}