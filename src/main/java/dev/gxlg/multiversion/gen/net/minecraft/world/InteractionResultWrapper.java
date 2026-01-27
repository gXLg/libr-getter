package dev.gxlg.multiversion.gen.net.minecraft.world;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class InteractionResultWrapper extends R.RWrapper<InteractionResultWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_1269/net.minecraft.world.InteractionResult");

    private int superCall = 0;

    protected InteractionResultWrapper(Object instance) {
        super(instance);
    }

    public static InteractionResultWrapper inst(Object instance) {
        return instance == null ? null : new InteractionResultWrapper(instance);
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.world.InteractionResultWrapper FAIL() {
        return dev.gxlg.multiversion.gen.net.minecraft.world.InteractionResultWrapper.inst(clazz.fld("field_5814/FAIL", dev.gxlg.multiversion.gen.net.minecraft.world.InteractionResultWrapper.clazz).get());
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") InteractionResultWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}