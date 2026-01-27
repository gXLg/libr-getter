package dev.gxlg.multiversion.gen.net.minecraft.tags;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class TagKeyWrapper extends R.RWrapper<TagKeyWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_6862/net.minecraft.tags.TagKey");

    private int superCall = 0;

    protected TagKeyWrapper(Object instance) {
        super(instance);
    }

    public static TagKeyWrapper inst(Object instance) {
        return instance == null ? null : new TagKeyWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") TagKeyWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}