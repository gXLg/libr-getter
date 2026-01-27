package dev.gxlg.multiversion.gen.net.minecraft.world.entity;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class LivingEntityWrapper extends dev.gxlg.multiversion.gen.net.minecraft.world.entity.EntityWrapper {
    public static final R.RClass clazz = R.clz("net.minecraft.class_1309/net.minecraft.world.entity.LivingEntity");

    private int superCall = 0;

    protected LivingEntityWrapper(Object instance) {
        super(instance);
    }

    public dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemStackWrapper getMainHandItem(){
        return dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemStackWrapper.inst(clazz.inst(this.instance).mthd("method_6047/getMainHandItem", dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemStackWrapper.clazz).invk());
    }

    public void lookAt(dev.gxlg.multiversion.gen.net.minecraft.commands.arguments.EntityAnchorArgument$AnchorWrapper anchor, dev.gxlg.multiversion.gen.net.minecraft.world.phys.Vec3Wrapper target){
        clazz.inst(this.instance).mthd("method_5702/lookAt", void.class, dev.gxlg.multiversion.gen.net.minecraft.commands.arguments.EntityAnchorArgument$AnchorWrapper.clazz, dev.gxlg.multiversion.gen.net.minecraft.world.phys.Vec3Wrapper.clazz).invk(anchor.unwrap(), target.unwrap());
    }

    public void setYHeadRot(float headYaw){
        clazz.inst(this.instance).mthd("method_5847/setYHeadRot", void.class, float.class).invk(headYaw);
    }

    public static LivingEntityWrapper inst(Object instance) {
        return instance == null ? null : new LivingEntityWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") LivingEntityWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return dev.gxlg.multiversion.gen.net.minecraft.world.entity.EntityWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}