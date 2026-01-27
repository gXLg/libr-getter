package dev.gxlg.multiversion.gen.net.minecraft.network.chat;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class HoverEvent$ShowTextWrapper extends dev.gxlg.multiversion.gen.net.minecraft.network.chat.HoverEventWrapper {
    public static final R.RClass clazz = R.clz("net.minecraft.class_2568$class_10613/net.minecraft.network.chat.HoverEvent$ShowText");

    private int superCall = 0;

    public HoverEvent$ShowTextWrapper(dev.gxlg.multiversion.gen.net.minecraft.network.chat.ComponentWrapper text) {
        this(clazz.constr(dev.gxlg.multiversion.gen.net.minecraft.network.chat.ComponentWrapper.clazz).newInst(text.unwrap()).self());
    }

    protected HoverEvent$ShowTextWrapper(Object instance) {
        super(instance);
    }

    public static HoverEvent$ShowTextWrapper inst(Object instance) {
        return instance == null ? null : new HoverEvent$ShowTextWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") HoverEvent$ShowTextWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return dev.gxlg.multiversion.gen.net.minecraft.network.chat.HoverEventWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}