package dev.gxlg.multiversion.gen.net.minecraft.core;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class Vec3iWrapper extends R.RWrapper<Vec3iWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_2382/net.minecraft.core.Vec3i");

    private int superCall = 0;

    protected Vec3iWrapper(Object instance) {
        super(instance);
    }

    public int getX(){
        return (int) clazz.inst(this.instance).mthd("method_10263/getX", int.class).invk();
    }

    public int getY(){
        return (int) clazz.inst(this.instance).mthd("method_10264/getY", int.class).invk();
    }

    public int getZ(){
        return (int) clazz.inst(this.instance).mthd("method_10260/getZ", int.class).invk();
    }

    public boolean closerThan(dev.gxlg.multiversion.gen.net.minecraft.core.Vec3iWrapper target, double distance){
        return (boolean) clazz.inst(this.instance).mthd("method_19771/closerThan", boolean.class, dev.gxlg.multiversion.gen.net.minecraft.core.Vec3iWrapper.clazz, double.class).invk(target.unwrap(), distance);
    }

    public static Vec3iWrapper inst(Object instance) {
        return instance == null ? null : new Vec3iWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") Vec3iWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}