package dev.gxlg.multiversion.gen.net.minecraft.world.item.enchantment;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class EnchantmentsWrapper extends R.RWrapper<EnchantmentsWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_1893/net.minecraft.world.item.enchantment.Enchantments");

    private int superCall = 0;

    protected EnchantmentsWrapper(Object instance) {
        super(instance);
    }

    public static EnchantmentsWrapper inst(Object instance) {
        return instance == null ? null : new EnchantmentsWrapper(instance);
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.world.item.enchantment.EnchantmentWrapper EFFICIENCY() {
        return dev.gxlg.multiversion.gen.net.minecraft.world.item.enchantment.EnchantmentWrapper.inst(clazz.fld("field_9131/EFFICIENCY", dev.gxlg.multiversion.gen.net.minecraft.world.item.enchantment.EnchantmentWrapper.clazz.self()).get());
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.resources.ResourceKeyWrapper EFFICIENCY2() {
        return dev.gxlg.multiversion.gen.net.minecraft.resources.ResourceKeyWrapper.inst(clazz.fld("field_9131/EFFICIENCY", dev.gxlg.multiversion.gen.net.minecraft.resources.ResourceKeyWrapper.clazz.self()).get());
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") EnchantmentsWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}