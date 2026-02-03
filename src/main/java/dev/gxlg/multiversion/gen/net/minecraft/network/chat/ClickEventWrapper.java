package dev.gxlg.multiversion.gen.net.minecraft.network.chat;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class ClickEventWrapper extends R.RWrapper<ClickEventWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_2558/net.minecraft.network.chat.ClickEvent");

    private int superCall = 0;

    public ClickEventWrapper(dev.gxlg.multiversion.gen.net.minecraft.network.chat.ClickEvent$ActionWrapper action, String value) {
        this(clazz.constr(dev.gxlg.multiversion.gen.net.minecraft.network.chat.ClickEvent$ActionWrapper.clazz.self(), String.class).newInst(action.unwrap(), value).self());
    }

    protected ClickEventWrapper(Object instance) {
        super(instance);
    }

    public static ClickEventWrapper inst(Object instance) {
        return instance == null ? null : new ClickEventWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") ClickEventWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}