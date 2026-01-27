package dev.gxlg.multiversion.gen.net.minecraft.world.level.block.state;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class BlockBehaviourWrapper extends R.RWrapper<BlockBehaviourWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_4970/net.minecraft.world.level.block.state.BlockBehaviour");

    private int superCall = 0;

    private final R.RDeclField hasCollisionAccessibleField;

    protected BlockBehaviourWrapper(Object instance) {
        super(instance);
        R.RInstance rInstance = clazz.inst(instance);
        this.hasCollisionAccessibleField = rInstance.dfld("field_23159/hasCollision", boolean.class);
    }

    public boolean getHasCollisionAccessibleField() {
        return (boolean) this.hasCollisionAccessibleField.get();
    }

    public static BlockBehaviourWrapper inst(Object instance) {
        return instance == null ? null : new BlockBehaviourWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") BlockBehaviourWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}