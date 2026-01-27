package dev.gxlg.multiversion.gen.net.minecraft.core;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class BlockPosWrapper extends dev.gxlg.multiversion.gen.net.minecraft.core.Vec3iWrapper {
    public static final R.RClass clazz = R.clz("net.minecraft.class_2338/net.minecraft.core.BlockPos");

    private int superCall = 0;

    protected BlockPosWrapper(Object instance) {
        super(instance);
    }

    public dev.gxlg.multiversion.gen.net.minecraft.core.BlockPosWrapper offset(int x, int y, int z){
        return dev.gxlg.multiversion.gen.net.minecraft.core.BlockPosWrapper.inst(clazz.inst(this.instance).mthd("method_10069/offset", dev.gxlg.multiversion.gen.net.minecraft.core.BlockPosWrapper.clazz, int.class, int.class, int.class).invk(x, y, z));
    }

    public dev.gxlg.multiversion.gen.net.minecraft.core.BlockPosWrapper relative(dev.gxlg.multiversion.gen.net.minecraft.core.DirectionWrapper direction){
        return dev.gxlg.multiversion.gen.net.minecraft.core.BlockPosWrapper.inst(clazz.inst(this.instance).mthd("method_10093/relative", dev.gxlg.multiversion.gen.net.minecraft.core.BlockPosWrapper.clazz, dev.gxlg.multiversion.gen.net.minecraft.core.DirectionWrapper.clazz).invk(direction.unwrap()));
    }

    public dev.gxlg.multiversion.gen.net.minecraft.core.BlockPosWrapper above(int distance){
        return dev.gxlg.multiversion.gen.net.minecraft.core.BlockPosWrapper.inst(clazz.inst(this.instance).mthd("method_10086/above", dev.gxlg.multiversion.gen.net.minecraft.core.BlockPosWrapper.clazz, int.class).invk(distance));
    }

    public dev.gxlg.multiversion.gen.net.minecraft.core.BlockPosWrapper below(){
        return dev.gxlg.multiversion.gen.net.minecraft.core.BlockPosWrapper.inst(clazz.inst(this.instance).mthd("method_10074/below", dev.gxlg.multiversion.gen.net.minecraft.core.BlockPosWrapper.clazz).invk());
    }

    public static BlockPosWrapper inst(Object instance) {
        return instance == null ? null : new BlockPosWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") BlockPosWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return dev.gxlg.multiversion.gen.net.minecraft.core.Vec3iWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}