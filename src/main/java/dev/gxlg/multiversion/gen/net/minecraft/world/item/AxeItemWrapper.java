package dev.gxlg.multiversion.gen.net.minecraft.world.item;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class AxeItemWrapper extends dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemWrapper {
    public static final R.RClass clazz = R.clz("net.minecraft.class_1743/net.minecraft.world.item.AxeItem");

    private int superCall = 0;

    protected AxeItemWrapper(Object instance) {
        super(instance);
    }

    public static AxeItemWrapper inst(Object instance) {
        return instance == null ? null : new AxeItemWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") AxeItemWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}