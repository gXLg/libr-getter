package dev.gxlg.multiversion.gen.net.fabricmc.fabric.api.event;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class EventWrapper extends R.RWrapper<EventWrapper> {
    public static final R.RClass clazz = R.clz("net.fabricmc.fabric.api.event.Event");

    private int superCall = 0;

    protected EventWrapper(Object instance) {
        super(instance);
    }

    public void register(Object listener){
        clazz.inst(this.instance).mthd("register", void.class, Object.class).invk(listener);
    }

    public static EventWrapper inst(Object instance) {
        return instance == null ? null : new EventWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") EventWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}