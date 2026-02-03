package dev.gxlg.multiversion.gen.net.minecraft.world.inventory;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class MenuTypeWrapper extends R.RWrapper<MenuTypeWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_3917/net.minecraft.world.inventory.MenuType");

    private int superCall = 0;

    protected MenuTypeWrapper(Object instance) {
        super(instance);
    }

    public static MenuTypeWrapper inst(Object instance) {
        return instance == null ? null : new MenuTypeWrapper(instance);
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.world.inventory.MenuTypeWrapper MERCHANT() {
        return dev.gxlg.multiversion.gen.net.minecraft.world.inventory.MenuTypeWrapper.inst(clazz.fld("field_17340/MERCHANT", dev.gxlg.multiversion.gen.net.minecraft.world.inventory.MenuTypeWrapper.clazz.self()).get());
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") MenuTypeWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}