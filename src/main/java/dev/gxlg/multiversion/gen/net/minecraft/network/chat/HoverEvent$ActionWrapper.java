package dev.gxlg.multiversion.gen.net.minecraft.network.chat;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class HoverEvent$ActionWrapper extends R.RWrapper<HoverEvent$ActionWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_2568$class_5247/net.minecraft.network.chat.HoverEvent$Action");

    private int superCall = 0;

    protected HoverEvent$ActionWrapper(Object instance) {
        super(instance);
    }

    public static HoverEvent$ActionWrapper inst(Object instance) {
        return instance == null ? null : new HoverEvent$ActionWrapper(instance);
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.network.chat.HoverEvent$ActionWrapper SHOW_TEXT() {
        return dev.gxlg.multiversion.gen.net.minecraft.network.chat.HoverEvent$ActionWrapper.inst(clazz.fld("field_24342/SHOW_TEXT", dev.gxlg.multiversion.gen.net.minecraft.network.chat.HoverEvent$ActionWrapper.clazz).get());
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") HoverEvent$ActionWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}