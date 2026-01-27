package dev.gxlg.multiversion.gen.net.minecraft.commands;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class SharedSuggestionProviderWrapper extends R.RWrapper<SharedSuggestionProviderWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_2172/net.minecraft.commands.SharedSuggestionProvider");

    private int superCall = 0;

    protected SharedSuggestionProviderWrapper(Object instance) {
        super(instance);
    }

    public static SharedSuggestionProviderWrapper inst(Object instance) {
        return instance == null ? null : new SharedSuggestionProviderWrapper(instance);
    }

    public static boolean matchesSubStr(String input, String search){
        return (boolean) clazz.mthd("method_27136/matchesSubStr", boolean.class, String.class, String.class).invk(input, search);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") SharedSuggestionProviderWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}