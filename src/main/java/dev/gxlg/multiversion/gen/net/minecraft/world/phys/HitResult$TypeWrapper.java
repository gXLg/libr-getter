package dev.gxlg.multiversion.gen.net.minecraft.world.phys;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class HitResult$TypeWrapper extends R.RWrapper<HitResult$TypeWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_239$class_240/net.minecraft.world.phys.HitResult$Type");

    private int superCall = 0;

    protected HitResult$TypeWrapper(Object instance) {
        super(instance);
    }

    public static HitResult$TypeWrapper inst(Object instance) {
        return instance == null ? null : new HitResult$TypeWrapper(instance);
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.world.phys.HitResult$TypeWrapper ENTITY() {
        return dev.gxlg.multiversion.gen.net.minecraft.world.phys.HitResult$TypeWrapper.inst(clazz.fld("field_1331/ENTITY", dev.gxlg.multiversion.gen.net.minecraft.world.phys.HitResult$TypeWrapper.clazz.self()).get());
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.world.phys.HitResult$TypeWrapper BLOCK() {
        return dev.gxlg.multiversion.gen.net.minecraft.world.phys.HitResult$TypeWrapper.inst(clazz.fld("field_1332/BLOCK", dev.gxlg.multiversion.gen.net.minecraft.world.phys.HitResult$TypeWrapper.clazz.self()).get());
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.world.phys.HitResult$TypeWrapper MISS() {
        return dev.gxlg.multiversion.gen.net.minecraft.world.phys.HitResult$TypeWrapper.inst(clazz.fld("field_1333/MISS", dev.gxlg.multiversion.gen.net.minecraft.world.phys.HitResult$TypeWrapper.clazz.self()).get());
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") HitResult$TypeWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}