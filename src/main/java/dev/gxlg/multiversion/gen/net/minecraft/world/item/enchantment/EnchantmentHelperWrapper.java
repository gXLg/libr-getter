package dev.gxlg.multiversion.gen.net.minecraft.world.item.enchantment;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class EnchantmentHelperWrapper extends R.RWrapper<EnchantmentHelperWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_1890/net.minecraft.world.item.enchantment.EnchantmentHelper");

    private int superCall = 0;

    protected EnchantmentHelperWrapper(Object instance) {
        super(instance);
    }

    public static EnchantmentHelperWrapper inst(Object instance) {
        return instance == null ? null : new EnchantmentHelperWrapper(instance);
    }

    public static int getItemEnchantmentLevel(dev.gxlg.multiversion.gen.net.minecraft.world.item.enchantment.EnchantmentWrapper enchantment, dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemStackWrapper stack){
        return (int) clazz.mthd("method_8225/getItemEnchantmentLevel", int.class, dev.gxlg.multiversion.gen.net.minecraft.world.item.enchantment.EnchantmentWrapper.clazz.self(), dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemStackWrapper.clazz.self()).invk(enchantment.unwrap(), stack.unwrap());
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") EnchantmentHelperWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}