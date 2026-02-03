package dev.gxlg.multiversion.gen.net.minecraft.core.registries;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class RegistriesWrapper extends R.RWrapper<RegistriesWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_7924/net.minecraft.core.registries.Registries");

    private int superCall = 0;

    protected RegistriesWrapper(Object instance) {
        super(instance);
    }

    public static RegistriesWrapper inst(Object instance) {
        return instance == null ? null : new RegistriesWrapper(instance);
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.resources.ResourceKeyWrapper ENCHANTMENT() {
        return dev.gxlg.multiversion.gen.net.minecraft.resources.ResourceKeyWrapper.inst(clazz.fld("field_41265/ENCHANTMENT", dev.gxlg.multiversion.gen.net.minecraft.resources.ResourceKeyWrapper.clazz.self()).get());
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") RegistriesWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}