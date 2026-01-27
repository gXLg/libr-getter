package dev.gxlg.multiversion.gen.net.minecraft.world;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class InteractionHandWrapper extends R.RWrapper<InteractionHandWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_1268/net.minecraft.world.InteractionHand");

    private int superCall = 0;

    protected InteractionHandWrapper(Object instance) {
        super(instance);
    }

    public static InteractionHandWrapper inst(Object instance) {
        return instance == null ? null : new InteractionHandWrapper(instance);
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.world.InteractionHandWrapper MAIN_HAND() {
        return dev.gxlg.multiversion.gen.net.minecraft.world.InteractionHandWrapper.inst(clazz.fld("field_5808/MAIN_HAND", dev.gxlg.multiversion.gen.net.minecraft.world.InteractionHandWrapper.clazz).get());
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.world.InteractionHandWrapper OFF_HAND() {
        return dev.gxlg.multiversion.gen.net.minecraft.world.InteractionHandWrapper.inst(clazz.fld("field_5810/OFF_HAND", dev.gxlg.multiversion.gen.net.minecraft.world.InteractionHandWrapper.clazz).get());
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") InteractionHandWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}