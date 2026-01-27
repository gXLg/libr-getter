package dev.gxlg.multiversion.gen.net.minecraft.commands;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class CommandBuildContextWrapper extends R.RWrapper<CommandBuildContextWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_7157/net.minecraft.commands.CommandBuildContext");

    private int superCall = 0;

    protected CommandBuildContextWrapper(Object instance) {
        super(instance);
    }

    public static CommandBuildContextWrapper inst(Object instance) {
        return instance == null ? null : new CommandBuildContextWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") CommandBuildContextWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}