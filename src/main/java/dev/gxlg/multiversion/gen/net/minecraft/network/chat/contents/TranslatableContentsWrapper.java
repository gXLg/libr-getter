package dev.gxlg.multiversion.gen.net.minecraft.network.chat.contents;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class TranslatableContentsWrapper extends dev.gxlg.multiversion.gen.net.minecraft.network.chat.MutableComponentWrapper {
    public static final R.RClass clazz = R.clz("net.minecraft.class_2588/net.minecraft.network.chat.contents.TranslatableContents");

    private int superCall = 0;

    public TranslatableContentsWrapper(String key, Object[] args) {
        this(clazz.constr(String.class, Object.class.arrayType()).newInst(key, args).self());
    }

    protected TranslatableContentsWrapper(Object instance) {
        super(instance);
    }

    public static TranslatableContentsWrapper inst(Object instance) {
        return instance == null ? null : new TranslatableContentsWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") TranslatableContentsWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return dev.gxlg.multiversion.gen.net.minecraft.network.chat.MutableComponentWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}