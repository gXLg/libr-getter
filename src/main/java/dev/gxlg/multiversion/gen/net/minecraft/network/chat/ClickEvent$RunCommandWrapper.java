package dev.gxlg.multiversion.gen.net.minecraft.network.chat;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class ClickEvent$RunCommandWrapper extends dev.gxlg.multiversion.gen.net.minecraft.network.chat.ClickEventWrapper {
    public static final R.RClass clazz = R.clz("net.minecraft.class_2558$class_10609/net.minecraft.network.chat.ClickEvent$RunCommand");

    private int superCall = 0;

    public ClickEvent$RunCommandWrapper(String command) {
        this(clazz.constr(String.class).newInst(command).self());
    }

    protected ClickEvent$RunCommandWrapper(Object instance) {
        super(instance);
    }

    public static ClickEvent$RunCommandWrapper inst(Object instance) {
        return instance == null ? null : new ClickEvent$RunCommandWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") ClickEvent$RunCommandWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return dev.gxlg.multiversion.gen.net.minecraft.network.chat.ClickEventWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}