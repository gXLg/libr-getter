package dev.gxlg.multiversion.gen.net.minecraft.core;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class Holder$ReferenceWrapper extends dev.gxlg.multiversion.gen.net.minecraft.core.HolderWrapper {
    public static final R.RClass clazz = R.clz("net.minecraft.class_6880$class_6883/net.minecraft.core.Holder$Reference");

    private int superCall = 0;

    protected Holder$ReferenceWrapper(Object instance) {
        super(instance);
    }

    public dev.gxlg.multiversion.gen.net.minecraft.resources.ResourceKeyWrapper key(){
        return dev.gxlg.multiversion.gen.net.minecraft.resources.ResourceKeyWrapper.inst(clazz.inst(this.instance).mthd("method_40237/key", dev.gxlg.multiversion.gen.net.minecraft.resources.ResourceKeyWrapper.clazz).invk());
    }

    public Object value(){
        return clazz.inst(this.instance).mthd("comp_349/value", Object.class).invk();
    }

    public static Holder$ReferenceWrapper inst(Object instance) {
        return instance == null ? null : new Holder$ReferenceWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") Holder$ReferenceWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return dev.gxlg.multiversion.gen.net.minecraft.core.HolderWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}