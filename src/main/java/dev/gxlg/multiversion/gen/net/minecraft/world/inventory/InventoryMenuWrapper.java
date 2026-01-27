package dev.gxlg.multiversion.gen.net.minecraft.world.inventory;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class InventoryMenuWrapper extends dev.gxlg.multiversion.gen.net.minecraft.world.inventory.AbstractContainerMenuWrapper {
    public static final R.RClass clazz = R.clz("net.minecraft.class_1723/net.minecraft.world.inventory.InventoryMenu");

    private int superCall = 0;

    protected InventoryMenuWrapper(Object instance) {
        super(instance);
    }

    public static InventoryMenuWrapper inst(Object instance) {
        return instance == null ? null : new InventoryMenuWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") InventoryMenuWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return dev.gxlg.multiversion.gen.net.minecraft.world.inventory.AbstractContainerMenuWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}