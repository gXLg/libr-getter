package dev.gxlg.multiversion.gen.net.minecraft.world.level.block;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class BlocksWrapper extends R.RWrapper<BlocksWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_2246/net.minecraft.world.level.block.Blocks");

    private int superCall = 0;

    protected BlocksWrapper(Object instance) {
        super(instance);
    }

    public static BlocksWrapper inst(Object instance) {
        return instance == null ? null : new BlocksWrapper(instance);
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.world.level.block.BlockWrapper LECTERN() {
        return dev.gxlg.multiversion.gen.net.minecraft.world.level.block.BlockWrapper.inst(clazz.fld("field_16330/LECTERN", dev.gxlg.multiversion.gen.net.minecraft.world.level.block.BlockWrapper.clazz.self()).get());
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.world.level.block.BlockWrapper AIR() {
        return dev.gxlg.multiversion.gen.net.minecraft.world.level.block.BlockWrapper.inst(clazz.fld("field_10124/AIR", dev.gxlg.multiversion.gen.net.minecraft.world.level.block.BlockWrapper.clazz.self()).get());
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") BlocksWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}