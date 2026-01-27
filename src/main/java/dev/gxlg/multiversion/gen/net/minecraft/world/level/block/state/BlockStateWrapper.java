package dev.gxlg.multiversion.gen.net.minecraft.world.level.block.state;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class BlockStateWrapper extends dev.gxlg.multiversion.gen.net.minecraft.world.level.block.state.BlockBehaviour$BlockStateBaseWrapper {
    public static final R.RClass clazz = R.clz("net.minecraft.class_2680/net.minecraft.world.level.block.state.BlockState");

    private int superCall = 0;

    protected BlockStateWrapper(Object instance) {
        super(instance);
    }

    public static BlockStateWrapper inst(Object instance) {
        return instance == null ? null : new BlockStateWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") BlockStateWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return dev.gxlg.multiversion.gen.net.minecraft.world.level.block.state.BlockBehaviour$BlockStateBaseWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}