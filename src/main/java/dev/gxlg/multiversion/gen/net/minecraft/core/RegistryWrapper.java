package dev.gxlg.multiversion.gen.net.minecraft.core;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class RegistryWrapper extends R.RWrapper<RegistryWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_2378/net.minecraft.core.Registry");

    private int superCall = 0;

    protected RegistryWrapper(Object instance) {
        super(instance);
    }

    public dev.gxlg.multiversion.gen.net.minecraft.resources.ResourceKeyWrapper key(){
        return dev.gxlg.multiversion.gen.net.minecraft.resources.ResourceKeyWrapper.inst(clazz.inst(this.instance).mthd("method_30517/key", dev.gxlg.multiversion.gen.net.minecraft.resources.ResourceKeyWrapper.clazz).invk());
    }

    public dev.gxlg.multiversion.gen.net.minecraft.resources.IdentifierWrapper getKey(Object resource){
        return dev.gxlg.multiversion.gen.net.minecraft.resources.IdentifierWrapper.inst(clazz.inst(this.instance).mthd("method_10221/getKey", dev.gxlg.multiversion.gen.net.minecraft.resources.IdentifierWrapper.clazz, Object.class).invk(resource));
    }

    public dev.gxlg.multiversion.gen.net.minecraft.core.HolderWrapper wrapAsHolder(Object value){
        return dev.gxlg.multiversion.gen.net.minecraft.core.HolderWrapper.inst(clazz.inst(this.instance).mthd("method_47983/wrapAsHolder", dev.gxlg.multiversion.gen.net.minecraft.core.HolderWrapper.clazz, Object.class).invk(value));
    }

    public static RegistryWrapper inst(Object instance) {
        return instance == null ? null : new RegistryWrapper(instance);
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.core.RegistryWrapper ENCHANTMENT() {
        return dev.gxlg.multiversion.gen.net.minecraft.core.RegistryWrapper.inst(clazz.fld("field_11160/ENCHANTMENT", dev.gxlg.multiversion.gen.net.minecraft.core.RegistryWrapper.clazz).get());
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") RegistryWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}