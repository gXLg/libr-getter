package dev.gxlg.multiversion.gen.net.minecraft.world.item;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class ItemWrapper extends R.RWrapper<ItemWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_1792/net.minecraft.world.item.Item");

    private int superCall = 0;

    protected ItemWrapper(Object instance) {
        super(instance);
    }

    public dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemStackWrapper getDefaultInstance(){
        return dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemStackWrapper.inst(clazz.inst(this.instance).mthd("method_7854/getDefaultInstance", dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemStackWrapper.clazz).invk());
    }

    public static ItemWrapper inst(Object instance) {
        return instance == null ? null : new ItemWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") ItemWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}