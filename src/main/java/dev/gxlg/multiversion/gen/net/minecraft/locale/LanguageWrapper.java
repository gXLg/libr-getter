package dev.gxlg.multiversion.gen.net.minecraft.locale;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class LanguageWrapper extends R.RWrapper<LanguageWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_2477/net.minecraft.locale.Language");

    private int superCall = 0;

    protected LanguageWrapper(Object instance) {
        super(instance);
    }

    public String getOrDefault(String translationKey){
        return (String) clazz.inst(this.instance).mthd("method_4679/getOrDefault", String.class, String.class).invk(translationKey);
    }

    public String getOrDefault2(String translationKey){
        return (String) clazz.inst(this.instance).mthd("method_48307/getOrDefault", String.class, String.class).invk(translationKey);
    }

    public static LanguageWrapper inst(Object instance) {
        return instance == null ? null : new LanguageWrapper(instance);
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.locale.LanguageWrapper getInstance(){
        return dev.gxlg.multiversion.gen.net.minecraft.locale.LanguageWrapper.inst(clazz.mthd("method_10517/getInstance", dev.gxlg.multiversion.gen.net.minecraft.locale.LanguageWrapper.clazz.self()).invk());
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") LanguageWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}