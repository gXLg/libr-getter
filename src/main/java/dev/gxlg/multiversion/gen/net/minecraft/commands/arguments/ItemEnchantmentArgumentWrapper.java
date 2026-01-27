package dev.gxlg.multiversion.gen.net.minecraft.commands.arguments;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class ItemEnchantmentArgumentWrapper extends R.RWrapper<ItemEnchantmentArgumentWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_2194/net.minecraft.commands.arguments.ItemEnchantmentArgument");

    private int superCall = 0;

    protected ItemEnchantmentArgumentWrapper(Object instance) {
        super(instance);
    }

    public static ItemEnchantmentArgumentWrapper inst(Object instance) {
        return instance == null ? null : new ItemEnchantmentArgumentWrapper(instance);
    }

    public static dev.gxlg.multiversion.gen.com.mojang.brigadier.arguments.ArgumentTypeWrapper enchantment(){
        return dev.gxlg.multiversion.gen.com.mojang.brigadier.arguments.ArgumentTypeWrapper.inst(clazz.mthd("method_9336/enchantment", dev.gxlg.multiversion.gen.com.mojang.brigadier.arguments.ArgumentTypeWrapper.clazz).invk());
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") ItemEnchantmentArgumentWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}