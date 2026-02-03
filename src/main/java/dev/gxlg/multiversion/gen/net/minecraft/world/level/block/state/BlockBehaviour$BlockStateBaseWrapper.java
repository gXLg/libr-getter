package dev.gxlg.multiversion.gen.net.minecraft.world.level.block.state;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class BlockBehaviour$BlockStateBaseWrapper extends R.RWrapper<BlockBehaviour$BlockStateBaseWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_4970$class_4971/net.minecraft.world.level.block.state.BlockBehaviour$BlockStateBase");

    private int superCall = 0;

    protected BlockBehaviour$BlockStateBaseWrapper(Object instance) {
        super(instance);
    }

    public boolean is(dev.gxlg.multiversion.gen.net.minecraft.world.level.block.BlockWrapper block){
        return (boolean) clazz.inst(this.instance).mthd("method_27852/is", boolean.class, dev.gxlg.multiversion.gen.net.minecraft.world.level.block.BlockWrapper.clazz.self()).invk(block.unwrap());
    }

    public dev.gxlg.multiversion.gen.net.minecraft.world.level.block.BlockWrapper getBlock(){
        return dev.gxlg.multiversion.gen.net.minecraft.world.level.block.BlockWrapper.inst(clazz.inst(this.instance).mthd("method_26204/getBlock", dev.gxlg.multiversion.gen.net.minecraft.world.level.block.BlockWrapper.clazz.self()).invk());
    }

    public boolean isAir(){
        return (boolean) clazz.inst(this.instance).mthd("method_26215/isAir", boolean.class).invk();
    }

    public static BlockBehaviour$BlockStateBaseWrapper inst(Object instance) {
        return instance == null ? null : new BlockBehaviour$BlockStateBaseWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") BlockBehaviour$BlockStateBaseWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}