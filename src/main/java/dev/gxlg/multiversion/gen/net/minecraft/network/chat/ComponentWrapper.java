package dev.gxlg.multiversion.gen.net.minecraft.network.chat;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class ComponentWrapper extends R.RWrapper<ComponentWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_2561/net.minecraft.network.chat.Component");

    private int superCall = 0;

    protected ComponentWrapper(Object instance) {
        super(instance);
    }

    public dev.gxlg.multiversion.gen.net.minecraft.network.chat.MutableComponentWrapper plainCopy(){
        return dev.gxlg.multiversion.gen.net.minecraft.network.chat.MutableComponentWrapper.inst(clazz.inst(this.instance).mthd("method_27662/plainCopy", dev.gxlg.multiversion.gen.net.minecraft.network.chat.MutableComponentWrapper.clazz).invk());
    }

    public static ComponentWrapper inst(Object instance) {
        return instance == null ? null : new ComponentWrapper(instance);
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.network.chat.MutableComponentWrapper translatable(String key, Object[] args){
        return dev.gxlg.multiversion.gen.net.minecraft.network.chat.MutableComponentWrapper.inst(clazz.mthd("method_43469/translatable", dev.gxlg.multiversion.gen.net.minecraft.network.chat.MutableComponentWrapper.clazz, String.class, Object.class.arrayType()).invk(key, args));
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.network.chat.ComponentWrapper nullToEmpty(String text){
        return dev.gxlg.multiversion.gen.net.minecraft.network.chat.ComponentWrapper.inst(clazz.mthd("method_30163/nullToEmpty", dev.gxlg.multiversion.gen.net.minecraft.network.chat.ComponentWrapper.clazz, String.class).invk(text));
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") ComponentWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}