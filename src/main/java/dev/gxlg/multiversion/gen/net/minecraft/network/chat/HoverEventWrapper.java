package dev.gxlg.multiversion.gen.net.minecraft.network.chat;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class HoverEventWrapper extends R.RWrapper<HoverEventWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_2568/net.minecraft.network.chat.HoverEvent");

    private int superCall = 0;

    public HoverEventWrapper(dev.gxlg.multiversion.gen.net.minecraft.network.chat.HoverEvent$ActionWrapper action, Object contents) {
        this(clazz.constr(dev.gxlg.multiversion.gen.net.minecraft.network.chat.HoverEvent$ActionWrapper.clazz, Object.class).newInst(action.unwrap(), contents).self());
    }

    protected HoverEventWrapper(Object instance) {
        super(instance);
    }

    public static HoverEventWrapper inst(Object instance) {
        return instance == null ? null : new HoverEventWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") HoverEventWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}