package dev.gxlg.multiversion.gen.net.minecraft.client;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class KeyMapping$CategoryWrapper extends R.RWrapper<KeyMapping$CategoryWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_304$class_11900/net.minecraft.client.KeyMapping$Category");

    private int superCall = 0;

    protected KeyMapping$CategoryWrapper(Object instance) {
        super(instance);
    }

    public static KeyMapping$CategoryWrapper inst(Object instance) {
        return instance == null ? null : new KeyMapping$CategoryWrapper(instance);
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.client.KeyMapping$CategoryWrapper register(dev.gxlg.multiversion.gen.net.minecraft.resources.IdentifierWrapper id){
        return dev.gxlg.multiversion.gen.net.minecraft.client.KeyMapping$CategoryWrapper.inst(clazz.mthd("method_74698/register", dev.gxlg.multiversion.gen.net.minecraft.client.KeyMapping$CategoryWrapper.clazz, dev.gxlg.multiversion.gen.net.minecraft.resources.IdentifierWrapper.clazz).invk(id.unwrap()));
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") KeyMapping$CategoryWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}