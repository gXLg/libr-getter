package dev.gxlg.multiversion.gen.net.minecraft.core.component;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class DataComponentHolderWrapper extends R.RWrapper<DataComponentHolderWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_9322/net.minecraft.core.component.DataComponentHolder");

    private int superCall = 0;

    protected DataComponentHolderWrapper(Object instance) {
        super(instance);
    }

    public Object get(dev.gxlg.multiversion.gen.net.minecraft.core.component.DataComponentTypeWrapper type){
        return clazz.inst(this.instance).mthd("method_58694/get", Object.class, dev.gxlg.multiversion.gen.net.minecraft.core.component.DataComponentTypeWrapper.clazz.self()).invk(type.unwrap());
    }

    public static DataComponentHolderWrapper inst(Object instance) {
        return instance == null ? null : new DataComponentHolderWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") DataComponentHolderWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}