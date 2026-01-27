package dev.gxlg.multiversion.gen.net.minecraft.core.registries;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class BuiltInRegistriesWrapper extends R.RWrapper<BuiltInRegistriesWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_7923/net.minecraft.core.registries.BuiltInRegistries");

    private int superCall = 0;

    protected BuiltInRegistriesWrapper(Object instance) {
        super(instance);
    }

    public static BuiltInRegistriesWrapper inst(Object instance) {
        return instance == null ? null : new BuiltInRegistriesWrapper(instance);
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.core.RegistryWrapper ENCHANTMENT() {
        return dev.gxlg.multiversion.gen.net.minecraft.core.RegistryWrapper.inst(clazz.fld("field_41176/ENCHANTMENT", dev.gxlg.multiversion.gen.net.minecraft.core.RegistryWrapper.clazz).get());
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") BuiltInRegistriesWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}