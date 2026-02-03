package dev.gxlg.multiversion.gen.net.minecraft.commands.arguments;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class ResourceOrTagArgumentWrapper extends dev.gxlg.multiversion.gen.com.mojang.brigadier.arguments.ArgumentTypeWrapper {
    public static final R.RClass clazz = R.clz("net.minecraft.class_7737/net.minecraft.commands.arguments.ResourceOrTagArgument");

    private int superCall = 0;

    protected ResourceOrTagArgumentWrapper(Object instance) {
        super(instance);
    }

    public static ResourceOrTagArgumentWrapper inst(Object instance) {
        return instance == null ? null : new ResourceOrTagArgumentWrapper(instance);
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.commands.arguments.ResourceOrTagArgumentWrapper resourceOrTag(dev.gxlg.multiversion.gen.net.minecraft.commands.CommandBuildContextWrapper context, dev.gxlg.multiversion.gen.net.minecraft.resources.ResourceKeyWrapper registryKey){
        return dev.gxlg.multiversion.gen.net.minecraft.commands.arguments.ResourceOrTagArgumentWrapper.inst(clazz.mthd("method_45637/resourceOrTag", dev.gxlg.multiversion.gen.net.minecraft.commands.arguments.ResourceOrTagArgumentWrapper.clazz.self(), dev.gxlg.multiversion.gen.net.minecraft.commands.CommandBuildContextWrapper.clazz.self(), dev.gxlg.multiversion.gen.net.minecraft.resources.ResourceKeyWrapper.clazz.self()).invk(context.unwrap(), registryKey.unwrap()));
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") ResourceOrTagArgumentWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return dev.gxlg.multiversion.gen.com.mojang.brigadier.arguments.ArgumentTypeWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}