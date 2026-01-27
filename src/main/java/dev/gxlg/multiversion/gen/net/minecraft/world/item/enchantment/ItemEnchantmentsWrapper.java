package dev.gxlg.multiversion.gen.net.minecraft.world.item.enchantment;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class ItemEnchantmentsWrapper extends R.RWrapper<ItemEnchantmentsWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_9304/net.minecraft.world.item.enchantment.ItemEnchantments");

    private int superCall = 0;

    protected ItemEnchantmentsWrapper(Object instance) {
        super(instance);
    }

    public java.util.Set<it.unimi.dsi.fastutil.objects.Object2IntMap.Entry<dev.gxlg.multiversion.gen.net.minecraft.core.HolderWrapper>> entrySet(){
        return dev.gxlg.multiversion.adapters.java.util.SetAdapter.wrapper(dev.gxlg.multiversion.adapters.it.unimi.dsi.fastutil.objects.Object2IntMap$EntryAdapter.wrapper(dev.gxlg.multiversion.gen.net.minecraft.core.HolderWrapper::inst)).apply(clazz.inst(this.instance).mthd("method_57539/entrySet", java.util.Set.class).invk());
    }

    public static ItemEnchantmentsWrapper inst(Object instance) {
        return instance == null ? null : new ItemEnchantmentsWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") ItemEnchantmentsWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}