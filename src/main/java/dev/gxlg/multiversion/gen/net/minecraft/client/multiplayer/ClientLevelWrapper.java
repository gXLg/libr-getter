package dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class ClientLevelWrapper extends dev.gxlg.multiversion.gen.net.minecraft.world.level.LevelWrapper {
    public static final R.RClass clazz = R.clz("net.minecraft.class_638/net.minecraft.client.multiplayer.ClientLevel");

    private int superCall = 0;

    protected ClientLevelWrapper(Object instance) {
        super(instance);
    }

    public java.lang.Iterable<dev.gxlg.multiversion.gen.net.minecraft.world.entity.EntityWrapper> entitiesForRendering(){
        return dev.gxlg.multiversion.adapters.java.lang.IterableAdapter.wrapper(dev.gxlg.multiversion.gen.net.minecraft.world.entity.EntityWrapper::inst).apply(clazz.inst(this.instance).mthd("method_18112/entitiesForRendering", java.lang.Iterable.class).invk());
    }

    public static ClientLevelWrapper inst(Object instance) {
        return instance == null ? null : new ClientLevelWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") ClientLevelWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return dev.gxlg.multiversion.gen.net.minecraft.world.level.LevelWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}