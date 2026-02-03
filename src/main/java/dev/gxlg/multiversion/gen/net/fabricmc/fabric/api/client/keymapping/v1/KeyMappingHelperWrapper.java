package dev.gxlg.multiversion.gen.net.fabricmc.fabric.api.client.keymapping.v1;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class KeyMappingHelperWrapper extends R.RWrapper<KeyMappingHelperWrapper> {
    public static final R.RClass clazz = R.clz("net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper");

    private int superCall = 0;

    protected KeyMappingHelperWrapper(Object instance) {
        super(instance);
    }

    public static KeyMappingHelperWrapper inst(Object instance) {
        return instance == null ? null : new KeyMappingHelperWrapper(instance);
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.client.KeyMappingWrapper registerKeyMapping(dev.gxlg.multiversion.gen.net.minecraft.client.KeyMappingWrapper keyBinding){
        return dev.gxlg.multiversion.gen.net.minecraft.client.KeyMappingWrapper.inst(clazz.mthd("registerKeyMapping", dev.gxlg.multiversion.gen.net.minecraft.client.KeyMappingWrapper.clazz.self(), dev.gxlg.multiversion.gen.net.minecraft.client.KeyMappingWrapper.clazz.self()).invk(keyBinding.unwrap()));
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") KeyMappingHelperWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}