package dev.gxlg.multiversion.gen.net.minecraft.client;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class KeyMappingWrapper extends R.RWrapper<KeyMappingWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_304/net.minecraft.client.KeyMapping");

    private int superCall = 0;

    public KeyMappingWrapper(String id, com.mojang.blaze3d.platform.InputConstants.Type type, int keyCode, dev.gxlg.multiversion.gen.net.minecraft.client.KeyMapping$CategoryWrapper category) {
        this(clazz.constr(String.class, com.mojang.blaze3d.platform.InputConstants.Type.class, int.class, dev.gxlg.multiversion.gen.net.minecraft.client.KeyMapping$CategoryWrapper.clazz.self()).newInst(id, type, keyCode, category.unwrap()).self());
    }

    public KeyMappingWrapper(String id, com.mojang.blaze3d.platform.InputConstants.Type type, int keyCode, String categoryId) {
        this(clazz.constr(String.class, com.mojang.blaze3d.platform.InputConstants.Type.class, int.class, String.class).newInst(id, type, keyCode, categoryId).self());
    }

    protected KeyMappingWrapper(Object instance) {
        super(instance);
    }

    public boolean consumeClick(){
        return (boolean) clazz.inst(this.instance).mthd("method_1436/consumeClick", boolean.class).invk();
    }

    public static KeyMappingWrapper inst(Object instance) {
        return instance == null ? null : new KeyMappingWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") KeyMappingWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}