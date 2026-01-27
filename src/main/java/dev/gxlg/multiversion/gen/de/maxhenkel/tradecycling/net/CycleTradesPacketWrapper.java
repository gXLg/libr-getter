package dev.gxlg.multiversion.gen.de.maxhenkel.tradecycling.net;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class CycleTradesPacketWrapper extends dev.gxlg.multiversion.gen.net.minecraft.network.protocol.common.custom.CustomPacketPayloadWrapper {
    public static final R.RClass clazz = R.clz("de.maxhenkel.tradecycling.net.CycleTradesPacket");

    private int superCall = 0;

    public CycleTradesPacketWrapper() {
        this(clazz.constr().newInst().self());
    }

    protected CycleTradesPacketWrapper(Object instance) {
        super(instance);
    }

    public static CycleTradesPacketWrapper inst(Object instance) {
        return instance == null ? null : new CycleTradesPacketWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") CycleTradesPacketWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return dev.gxlg.multiversion.gen.net.minecraft.network.protocol.common.custom.CustomPacketPayloadWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}