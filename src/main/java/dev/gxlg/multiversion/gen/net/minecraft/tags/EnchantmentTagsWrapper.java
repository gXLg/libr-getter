package dev.gxlg.multiversion.gen.net.minecraft.tags;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class EnchantmentTagsWrapper extends R.RWrapper<EnchantmentTagsWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_9636/net.minecraft.tags.EnchantmentTags");

    private int superCall = 0;

    protected EnchantmentTagsWrapper(Object instance) {
        super(instance);
    }

    public static EnchantmentTagsWrapper inst(Object instance) {
        return instance == null ? null : new EnchantmentTagsWrapper(instance);
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.tags.TagKeyWrapper TRADEABLE() {
        return dev.gxlg.multiversion.gen.net.minecraft.tags.TagKeyWrapper.inst(clazz.fld("field_51545/TRADEABLE", dev.gxlg.multiversion.gen.net.minecraft.tags.TagKeyWrapper.clazz.self()).get());
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") EnchantmentTagsWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}