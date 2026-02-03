package dev.gxlg.multiversion.gen.net.minecraft.world.inventory;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class ClickTypeWrapper extends R.RWrapper<ClickTypeWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_1713/net.minecraft.world.inventory.ClickType");

    private int superCall = 0;

    protected ClickTypeWrapper(Object instance) {
        super(instance);
    }

    public static ClickTypeWrapper inst(Object instance) {
        return instance == null ? null : new ClickTypeWrapper(instance);
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.world.inventory.ClickTypeWrapper PICKUP() {
        return dev.gxlg.multiversion.gen.net.minecraft.world.inventory.ClickTypeWrapper.inst(clazz.fld("field_7790/PICKUP", dev.gxlg.multiversion.gen.net.minecraft.world.inventory.ClickTypeWrapper.clazz.self()).get());
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.world.inventory.ClickTypeWrapper SWAP() {
        return dev.gxlg.multiversion.gen.net.minecraft.world.inventory.ClickTypeWrapper.inst(clazz.fld("field_7791/SWAP", dev.gxlg.multiversion.gen.net.minecraft.world.inventory.ClickTypeWrapper.clazz.self()).get());
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") ClickTypeWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}