package dev.gxlg.multiversion.gen.de.maxhenkel.tradecycling;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class TradeCyclingClientModWrapper extends R.RWrapper<TradeCyclingClientModWrapper> {
    public static final R.RClass clazz = R.clz("de.maxhenkel.tradecycling.TradeCyclingClientMod");

    private int superCall = 0;

    protected TradeCyclingClientModWrapper(Object instance) {
        super(instance);
    }

    public static TradeCyclingClientModWrapper inst(Object instance) {
        return instance == null ? null : new TradeCyclingClientModWrapper(instance);
    }

    public static void sendCycleTradesPacket(){
        clazz.mthd("sendCycleTradesPacket", void.class).invk();
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") TradeCyclingClientModWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}