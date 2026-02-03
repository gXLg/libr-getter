package dev.gxlg.multiversion.gen.net.fabricmc.fabric.api.client.command.v2;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class ClientCommandsWrapper extends R.RWrapper<ClientCommandsWrapper> {
    public static final R.RClass clazz = R.clz("net.fabricmc.fabric.api.client.command.v2.ClientCommands");

    private int superCall = 0;

    protected ClientCommandsWrapper(Object instance) {
        super(instance);
    }

    public static ClientCommandsWrapper inst(Object instance) {
        return instance == null ? null : new ClientCommandsWrapper(instance);
    }

    public static dev.gxlg.multiversion.gen.com.mojang.brigadier.builder.LiteralArgumentBuilderWrapper literal(String name){
        return dev.gxlg.multiversion.gen.com.mojang.brigadier.builder.LiteralArgumentBuilderWrapper.inst(clazz.mthd("literal", dev.gxlg.multiversion.gen.com.mojang.brigadier.builder.LiteralArgumentBuilderWrapper.clazz.self(), String.class).invk(name));
    }

    public static dev.gxlg.multiversion.gen.com.mojang.brigadier.builder.ArgumentBuilderWrapper argument(String name, dev.gxlg.multiversion.gen.com.mojang.brigadier.arguments.ArgumentTypeWrapper argumentType){
        return dev.gxlg.multiversion.gen.com.mojang.brigadier.builder.ArgumentBuilderWrapper.inst(clazz.mthd("argument", dev.gxlg.multiversion.gen.com.mojang.brigadier.builder.ArgumentBuilderWrapper.clazz.self(), String.class, dev.gxlg.multiversion.gen.com.mojang.brigadier.arguments.ArgumentTypeWrapper.clazz.self()).invk(name, argumentType.unwrap()));
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") ClientCommandsWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}