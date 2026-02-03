package dev.gxlg.multiversion.gen.net.minecraft.world.phys;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class BlockHitResultWrapper extends dev.gxlg.multiversion.gen.net.minecraft.world.phys.HitResultWrapper {
    public static final R.RClass clazz = R.clz("net.minecraft.class_3965/net.minecraft.world.phys.BlockHitResult");

    private int superCall = 0;

    public BlockHitResultWrapper(dev.gxlg.multiversion.gen.net.minecraft.world.phys.Vec3Wrapper hitPoint, dev.gxlg.multiversion.gen.net.minecraft.core.DirectionWrapper side, dev.gxlg.multiversion.gen.net.minecraft.core.BlockPosWrapper hitBlockPos, boolean inside) {
        this(clazz.constr(dev.gxlg.multiversion.gen.net.minecraft.world.phys.Vec3Wrapper.clazz.self(), dev.gxlg.multiversion.gen.net.minecraft.core.DirectionWrapper.clazz.self(), dev.gxlg.multiversion.gen.net.minecraft.core.BlockPosWrapper.clazz.self(), boolean.class).newInst(hitPoint.unwrap(), side.unwrap(), hitBlockPos.unwrap(), inside).self());
    }

    protected BlockHitResultWrapper(Object instance) {
        super(instance);
    }

    public dev.gxlg.multiversion.gen.net.minecraft.core.BlockPosWrapper getBlockPos(){
        return dev.gxlg.multiversion.gen.net.minecraft.core.BlockPosWrapper.inst(clazz.inst(this.instance).mthd("method_17777/getBlockPos", dev.gxlg.multiversion.gen.net.minecraft.core.BlockPosWrapper.clazz.self()).invk());
    }

    public dev.gxlg.multiversion.gen.net.minecraft.core.DirectionWrapper getDirection(){
        return dev.gxlg.multiversion.gen.net.minecraft.core.DirectionWrapper.inst(clazz.inst(this.instance).mthd("method_17780/getDirection", dev.gxlg.multiversion.gen.net.minecraft.core.DirectionWrapper.clazz.self()).invk());
    }

    public static BlockHitResultWrapper inst(Object instance) {
        return instance == null ? null : new BlockHitResultWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") BlockHitResultWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return dev.gxlg.multiversion.gen.net.minecraft.world.phys.HitResultWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}