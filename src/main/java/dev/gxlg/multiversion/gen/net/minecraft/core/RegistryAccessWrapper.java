package dev.gxlg.multiversion.gen.net.minecraft.core;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class RegistryAccessWrapper extends R.RWrapper<RegistryAccessWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_5455/net.minecraft.core.RegistryAccess");

    private int superCall = 0;

    protected RegistryAccessWrapper(Object instance) {
        super(instance);
    }

    public dev.gxlg.multiversion.gen.net.minecraft.core.RegistryWrapper lookupOrThrow(dev.gxlg.multiversion.gen.net.minecraft.resources.ResourceKeyWrapper key){
        return dev.gxlg.multiversion.gen.net.minecraft.core.RegistryWrapper.inst(clazz.inst(this.instance).mthd("method_30530/registryOrThrow/lookupOrThrow", dev.gxlg.multiversion.gen.net.minecraft.core.RegistryWrapper.clazz.self(), dev.gxlg.multiversion.gen.net.minecraft.resources.ResourceKeyWrapper.clazz.self()).invk(key.unwrap()));
    }

    public static RegistryAccessWrapper inst(Object instance) {
        return instance == null ? null : new RegistryAccessWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") RegistryAccessWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}