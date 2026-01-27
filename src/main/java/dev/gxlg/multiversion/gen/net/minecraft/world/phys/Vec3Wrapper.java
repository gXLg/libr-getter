package dev.gxlg.multiversion.gen.net.minecraft.world.phys;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class Vec3Wrapper extends R.RWrapper<Vec3Wrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_243/net.minecraft.world.phys.Vec3");

    private int superCall = 0;

    public Vec3Wrapper(double x, double y, double z) {
        this(clazz.constr(double.class, double.class, double.class).newInst(x, y, z).self());
    }

    protected Vec3Wrapper(Object instance) {
        super(instance);
    }

    public double x(){
        return (double) clazz.inst(this.instance).mthd("method_10216/x", double.class).invk();
    }

    public double y(){
        return (double) clazz.inst(this.instance).mthd("method_10214/y", double.class).invk();
    }

    public double z(){
        return (double) clazz.inst(this.instance).mthd("method_10215/z", double.class).invk();
    }

    public static Vec3Wrapper inst(Object instance) {
        return instance == null ? null : new Vec3Wrapper(instance);
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.world.phys.Vec3Wrapper atBottomCenterOf(dev.gxlg.multiversion.gen.net.minecraft.core.Vec3iWrapper pos){
        return dev.gxlg.multiversion.gen.net.minecraft.world.phys.Vec3Wrapper.inst(clazz.mthd("method_24955/atBottomCenterOf", dev.gxlg.multiversion.gen.net.minecraft.world.phys.Vec3Wrapper.clazz, dev.gxlg.multiversion.gen.net.minecraft.core.Vec3iWrapper.clazz).invk(pos.unwrap()));
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.world.phys.Vec3Wrapper atCenterOf(dev.gxlg.multiversion.gen.net.minecraft.core.Vec3iWrapper pos){
        return dev.gxlg.multiversion.gen.net.minecraft.world.phys.Vec3Wrapper.inst(clazz.mthd("method_24953/atCenterOf", dev.gxlg.multiversion.gen.net.minecraft.world.phys.Vec3Wrapper.clazz, dev.gxlg.multiversion.gen.net.minecraft.core.Vec3iWrapper.clazz).invk(pos.unwrap()));
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") Vec3Wrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}