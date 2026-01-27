package dev.gxlg.multiversion.gen.net.minecraft.core;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class HolderWrapper extends R.RWrapper<HolderWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_6880/net.minecraft.core.Holder");

    private int superCall = 0;

    protected HolderWrapper(Object instance) {
        super(instance);
    }

    public boolean is(dev.gxlg.multiversion.gen.net.minecraft.resources.ResourceKeyWrapper resourceKey){
        return (boolean) clazz.inst(this.instance).mthd("method_40225/is", boolean.class, dev.gxlg.multiversion.gen.net.minecraft.resources.ResourceKeyWrapper.clazz).invk(resourceKey.unwrap());
    }

    public boolean is(dev.gxlg.multiversion.gen.net.minecraft.tags.TagKeyWrapper resourceKey){
        return (boolean) clazz.inst(this.instance).mthd("method_40220/is", boolean.class, dev.gxlg.multiversion.gen.net.minecraft.tags.TagKeyWrapper.clazz).invk(resourceKey.unwrap());
    }

    public String getRegisteredName(){
        return (String) clazz.inst(this.instance).mthd("method_55840/getRegisteredName", String.class).invk();
    }

    public static HolderWrapper inst(Object instance) {
        return instance == null ? null : new HolderWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") HolderWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}