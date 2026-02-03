package dev.gxlg.multiversion.gen.net.minecraft.commands.arguments;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class ResourceOrTagArgument$ResultWrapper extends R.RWrapper<ResourceOrTagArgument$ResultWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_7737$class_7741/net.minecraft.commands.arguments.ResourceOrTagArgument$Result");

    private int superCall = 0;

    protected ResourceOrTagArgument$ResultWrapper(Object instance) {
        super(instance);
    }

    public com.mojang.datafixers.util.Either unwrap2(){
        return (com.mojang.datafixers.util.Either) clazz.inst(this.instance).mthd("method_45647/unwrap", com.mojang.datafixers.util.Either.class).invk();
    }

    public java.util.Optional cast(dev.gxlg.multiversion.gen.net.minecraft.resources.ResourceKeyWrapper registryKey){
        return (java.util.Optional) clazz.inst(this.instance).mthd("method_45648/cast", java.util.Optional.class, dev.gxlg.multiversion.gen.net.minecraft.resources.ResourceKeyWrapper.clazz.self()).invk(registryKey.unwrap());
    }

    public static ResourceOrTagArgument$ResultWrapper inst(Object instance) {
        return instance == null ? null : new ResourceOrTagArgument$ResultWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") ResourceOrTagArgument$ResultWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}