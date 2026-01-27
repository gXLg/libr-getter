package dev.gxlg.multiversion.gen.net.minecraft.nbt;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class TagWrapper extends R.RWrapper<TagWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_2520/net.minecraft.nbt.Tag");

    private int superCall = 0;

    protected TagWrapper(Object instance) {
        super(instance);
    }

    public byte getId(){
        return (byte) clazz.inst(this.instance).mthd("method_10711/getId", byte.class).invk();
    }

    public static TagWrapper inst(Object instance) {
        return instance == null ? null : new TagWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") TagWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}