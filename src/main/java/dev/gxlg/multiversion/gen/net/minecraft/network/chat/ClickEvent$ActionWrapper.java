package dev.gxlg.multiversion.gen.net.minecraft.network.chat;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class ClickEvent$ActionWrapper extends R.RWrapper<ClickEvent$ActionWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_2558$class_2559/net.minecraft.network.chat.ClickEvent$Action");

    private int superCall = 0;

    protected ClickEvent$ActionWrapper(Object instance) {
        super(instance);
    }

    public static ClickEvent$ActionWrapper inst(Object instance) {
        return instance == null ? null : new ClickEvent$ActionWrapper(instance);
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.network.chat.ClickEvent$ActionWrapper RUN_COMMAND() {
        return dev.gxlg.multiversion.gen.net.minecraft.network.chat.ClickEvent$ActionWrapper.inst(clazz.fld("field_11750/RUN_COMMAND", dev.gxlg.multiversion.gen.net.minecraft.network.chat.ClickEvent$ActionWrapper.clazz.self()).get());
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.network.chat.ClickEvent$ActionWrapper CHANGE_PAGE() {
        return dev.gxlg.multiversion.gen.net.minecraft.network.chat.ClickEvent$ActionWrapper.inst(clazz.fld("field_11748/CHANGE_PAGE", dev.gxlg.multiversion.gen.net.minecraft.network.chat.ClickEvent$ActionWrapper.clazz.self()).get());
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") ClickEvent$ActionWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}