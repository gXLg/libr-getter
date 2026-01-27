package dev.gxlg.multiversion.gen.com.mojang.brigadier.builder;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class ArgumentBuilderWrapper extends R.RWrapper<ArgumentBuilderWrapper> {
    public static final R.RClass clazz = R.clz("com.mojang.brigadier.builder.ArgumentBuilder");

    private int superCall = 0;

    protected ArgumentBuilderWrapper(Object instance) {
        super(instance);
    }

    public dev.gxlg.multiversion.gen.com.mojang.brigadier.builder.ArgumentBuilderWrapper then(dev.gxlg.multiversion.gen.com.mojang.brigadier.builder.ArgumentBuilderWrapper argument){
        return dev.gxlg.multiversion.gen.com.mojang.brigadier.builder.ArgumentBuilderWrapper.inst(clazz.inst(this.instance).mthd("then", dev.gxlg.multiversion.gen.com.mojang.brigadier.builder.ArgumentBuilderWrapper.clazz, dev.gxlg.multiversion.gen.com.mojang.brigadier.builder.ArgumentBuilderWrapper.clazz).invk(argument.unwrap()));
    }

    public dev.gxlg.multiversion.gen.com.mojang.brigadier.builder.ArgumentBuilderWrapper executes(com.mojang.brigadier.Command command){
        return dev.gxlg.multiversion.gen.com.mojang.brigadier.builder.ArgumentBuilderWrapper.inst(clazz.inst(this.instance).mthd("executes", dev.gxlg.multiversion.gen.com.mojang.brigadier.builder.ArgumentBuilderWrapper.clazz, com.mojang.brigadier.Command.class).invk(command));
    }

    public static ArgumentBuilderWrapper inst(Object instance) {
        return instance == null ? null : new ArgumentBuilderWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") ArgumentBuilderWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}