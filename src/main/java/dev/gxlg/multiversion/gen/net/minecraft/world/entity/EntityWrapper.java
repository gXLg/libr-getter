package dev.gxlg.multiversion.gen.net.minecraft.world.entity;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class EntityWrapper extends R.RWrapper<EntityWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_1297/net.minecraft.world.entity.Entity");

    private int superCall = 0;

    protected EntityWrapper(Object instance) {
        super(instance);
    }

    public boolean isPassenger(){
        return (boolean) clazz.inst(this.instance).mthd("method_5765/isPassenger", boolean.class).invk();
    }

    public dev.gxlg.multiversion.gen.net.minecraft.core.BlockPosWrapper blockPosition(){
        return dev.gxlg.multiversion.gen.net.minecraft.core.BlockPosWrapper.inst(clazz.inst(this.instance).mthd("method_24515/blockPosition", dev.gxlg.multiversion.gen.net.minecraft.core.BlockPosWrapper.clazz).invk());
    }

    public float distanceTo(dev.gxlg.multiversion.gen.net.minecraft.world.entity.EntityWrapper entity){
        return (float) clazz.inst(this.instance).mthd("method_5739/distanceTo", float.class, dev.gxlg.multiversion.gen.net.minecraft.world.entity.EntityWrapper.clazz).invk(entity.unwrap());
    }

    public double getX(){
        return (double) clazz.inst(this.instance).mthd("method_23317/getX", double.class).invk();
    }

    public double getY(){
        return (double) clazz.inst(this.instance).mthd("method_23318/getY", double.class).invk();
    }

    public double getZ(){
        return (double) clazz.inst(this.instance).mthd("method_23321/getZ", double.class).invk();
    }

    public float getYRot(){
        return (float) clazz.inst(this.instance).mthd("method_36454/getYRot", float.class).invk();
    }

    public void setYRot(float yaw){
        clazz.inst(this.instance).mthd("method_36456/setYRot", void.class, float.class).invk(yaw);
    }

    public float getXRot(){
        return (float) clazz.inst(this.instance).mthd("method_36455/getXRot", float.class).invk();
    }

    public void setXRot(float pitch){
        clazz.inst(this.instance).mthd("method_36457/setXRot", void.class, float.class).invk(pitch);
    }

    public static EntityWrapper inst(Object instance) {
        return instance == null ? null : new EntityWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") EntityWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}