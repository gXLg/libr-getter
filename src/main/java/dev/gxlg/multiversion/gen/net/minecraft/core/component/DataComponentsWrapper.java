package dev.gxlg.multiversion.gen.net.minecraft.core.component;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class DataComponentsWrapper extends R.RWrapper<DataComponentsWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_9334/net.minecraft.core.component.DataComponents");

    private int superCall = 0;

    protected DataComponentsWrapper(Object instance) {
        super(instance);
    }

    public static DataComponentsWrapper inst(Object instance) {
        return instance == null ? null : new DataComponentsWrapper(instance);
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.core.component.DataComponentTypeWrapper CUSTOM_DATA() {
        return dev.gxlg.multiversion.gen.net.minecraft.core.component.DataComponentTypeWrapper.inst(clazz.fld("field_49628/CUSTOM_DATA", dev.gxlg.multiversion.gen.net.minecraft.core.component.DataComponentTypeWrapper.clazz).get());
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.core.component.DataComponentTypeWrapper ENCHANTMENTS() {
        return dev.gxlg.multiversion.gen.net.minecraft.core.component.DataComponentTypeWrapper.inst(clazz.fld("field_49633/ENCHANTMENTS", dev.gxlg.multiversion.gen.net.minecraft.core.component.DataComponentTypeWrapper.clazz).get());
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.core.component.DataComponentTypeWrapper STORED_ENCHANTMENTS() {
        return dev.gxlg.multiversion.gen.net.minecraft.core.component.DataComponentTypeWrapper.inst(clazz.fld("field_49643/STORED_ENCHANTMENTS", dev.gxlg.multiversion.gen.net.minecraft.core.component.DataComponentTypeWrapper.clazz).get());
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") DataComponentsWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}