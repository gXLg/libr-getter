package dev.gxlg.multiversion.gen.net.minecraft.world.item.component;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class CustomDataWrapper extends R.RWrapper<CustomDataWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_9279/net.minecraft.world.item.component.CustomData");

    private int superCall = 0;

    protected CustomDataWrapper(Object instance) {
        super(instance);
    }

    public dev.gxlg.multiversion.gen.net.minecraft.nbt.CompoundTagWrapper copyTag(){
        return dev.gxlg.multiversion.gen.net.minecraft.nbt.CompoundTagWrapper.inst(clazz.inst(this.instance).mthd("method_57461/copyTag", dev.gxlg.multiversion.gen.net.minecraft.nbt.CompoundTagWrapper.clazz.self()).invk());
    }

    public static CustomDataWrapper inst(Object instance) {
        return instance == null ? null : new CustomDataWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") CustomDataWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}