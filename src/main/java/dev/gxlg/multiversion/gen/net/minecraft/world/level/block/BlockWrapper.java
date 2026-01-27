package dev.gxlg.multiversion.gen.net.minecraft.world.level.block;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class BlockWrapper extends dev.gxlg.multiversion.gen.net.minecraft.world.level.block.state.BlockBehaviourWrapper {
    public static final R.RClass clazz = R.clz("net.minecraft.class_2248/net.minecraft.world.level.block.Block");

    private int superCall = 0;

    protected BlockWrapper(Object instance) {
        super(instance);
    }

    public dev.gxlg.multiversion.gen.net.minecraft.world.level.block.state.BlockStateWrapper defaultBlockState(){
        return dev.gxlg.multiversion.gen.net.minecraft.world.level.block.state.BlockStateWrapper.inst(clazz.inst(this.instance).mthd("method_9564/defaultBlockState", dev.gxlg.multiversion.gen.net.minecraft.world.level.block.state.BlockStateWrapper.clazz).invk());
    }

    public static BlockWrapper inst(Object instance) {
        return instance == null ? null : new BlockWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") BlockWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return dev.gxlg.multiversion.gen.net.minecraft.world.level.block.state.BlockBehaviourWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}