package dev.gxlg.multiversion.gen.net.minecraft.world.item.enchantment;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class EnchantmentWrapper extends R.RWrapper<EnchantmentWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_1887/net.minecraft.world.item.enchantment.Enchantment");

    private int superCall = 0;

    protected EnchantmentWrapper(Object instance) {
        super(instance);
    }

    public boolean isTradeable(){
        return (boolean) clazz.inst(this.instance).mthd("method_25949/isTradeable", boolean.class).invk();
    }

    public int getMaxLevel(){
        return (int) clazz.inst(this.instance).mthd("method_8183/getMaxLevel", int.class).invk();
    }

    public static EnchantmentWrapper inst(Object instance) {
        return instance == null ? null : new EnchantmentWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") EnchantmentWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}