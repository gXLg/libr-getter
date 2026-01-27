package dev.gxlg.multiversion.gen.net.fabricmc.fabric.api.client.keybinding.v1;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class KeyBindingHelperWrapper extends R.RWrapper<KeyBindingHelperWrapper> {
    public static final R.RClass clazz = R.clz("net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper");

    private int superCall = 0;

    protected KeyBindingHelperWrapper(Object instance) {
        super(instance);
    }

    public static KeyBindingHelperWrapper inst(Object instance) {
        return instance == null ? null : new KeyBindingHelperWrapper(instance);
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.client.KeyMappingWrapper registerKeyBinding(dev.gxlg.multiversion.gen.net.minecraft.client.KeyMappingWrapper keyBinding){
        return dev.gxlg.multiversion.gen.net.minecraft.client.KeyMappingWrapper.inst(clazz.mthd("registerKeyBinding", dev.gxlg.multiversion.gen.net.minecraft.client.KeyMappingWrapper.clazz, dev.gxlg.multiversion.gen.net.minecraft.client.KeyMappingWrapper.clazz).invk(keyBinding.unwrap()));
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") KeyBindingHelperWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}