package dev.gxlg.multiversion.gen.net.minecraft.world.phys;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class HitResultWrapper extends R.RWrapper<HitResultWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_239/net.minecraft.world.phys.HitResult");

    private int superCall = 0;

    protected HitResultWrapper(Object instance) {
        super(instance);
    }

    public dev.gxlg.multiversion.gen.net.minecraft.world.phys.HitResult$TypeWrapper getType(){
        return dev.gxlg.multiversion.gen.net.minecraft.world.phys.HitResult$TypeWrapper.inst(clazz.inst(this.instance).mthd("method_17783/getType", dev.gxlg.multiversion.gen.net.minecraft.world.phys.HitResult$TypeWrapper.clazz.self()).invk());
    }

    public static HitResultWrapper inst(Object instance) {
        return instance == null ? null : new HitResultWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") HitResultWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}