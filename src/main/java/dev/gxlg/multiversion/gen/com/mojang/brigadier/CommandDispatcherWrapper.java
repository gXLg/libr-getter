package dev.gxlg.multiversion.gen.com.mojang.brigadier;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class CommandDispatcherWrapper extends R.RWrapper<CommandDispatcherWrapper> {
    public static final R.RClass clazz = R.clz("com.mojang.brigadier.CommandDispatcher");

    private int superCall = 0;

    protected CommandDispatcherWrapper(Object instance) {
        super(instance);
    }

    public com.mojang.brigadier.tree.LiteralCommandNode register(dev.gxlg.multiversion.gen.com.mojang.brigadier.builder.LiteralArgumentBuilderWrapper command){
        return (com.mojang.brigadier.tree.LiteralCommandNode) clazz.inst(this.instance).mthd("register", com.mojang.brigadier.tree.LiteralCommandNode.class, dev.gxlg.multiversion.gen.com.mojang.brigadier.builder.LiteralArgumentBuilderWrapper.clazz).invk(command.unwrap());
    }

    public static CommandDispatcherWrapper inst(Object instance) {
        return instance == null ? null : new CommandDispatcherWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") CommandDispatcherWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}