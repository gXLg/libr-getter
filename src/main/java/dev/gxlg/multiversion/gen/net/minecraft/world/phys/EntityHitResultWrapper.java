package dev.gxlg.multiversion.gen.net.minecraft.world.phys;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class EntityHitResultWrapper extends dev.gxlg.multiversion.gen.net.minecraft.world.phys.HitResultWrapper {
    public static final R.RClass clazz = R.clz("net.minecraft.class_3966/net.minecraft.world.phys.EntityHitResult");

    private int superCall = 0;

    protected EntityHitResultWrapper(Object instance) {
        super(instance);
    }

    public dev.gxlg.multiversion.gen.net.minecraft.world.entity.EntityWrapper getEntity(){
        return dev.gxlg.multiversion.gen.net.minecraft.world.entity.EntityWrapper.inst(clazz.inst(this.instance).mthd("method_17782/getEntity", dev.gxlg.multiversion.gen.net.minecraft.world.entity.EntityWrapper.clazz).invk());
    }

    public static EntityHitResultWrapper inst(Object instance) {
        return instance == null ? null : new EntityHitResultWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") EntityHitResultWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return dev.gxlg.multiversion.gen.net.minecraft.world.phys.HitResultWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}