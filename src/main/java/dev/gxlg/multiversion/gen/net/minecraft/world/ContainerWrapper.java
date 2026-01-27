package dev.gxlg.multiversion.gen.net.minecraft.world;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class ContainerWrapper extends R.RWrapper<ContainerWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_1263/net.minecraft.world.Container");

    private int superCall = 0;

    protected ContainerWrapper(Object instance) {
        super(instance);
    }

    public int countItem(dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemWrapper item){
        return (int) clazz.inst(this.instance).mthd("method_18861/countItem", int.class, dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemWrapper.clazz).invk(item.unwrap());
    }

    public dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemStackWrapper getItem(int index){
        return dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemStackWrapper.inst(clazz.inst(this.instance).mthd("method_5438/getItem", dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemStackWrapper.clazz, int.class).invk(index));
    }

    public static ContainerWrapper inst(Object instance) {
        return instance == null ? null : new ContainerWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") ContainerWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}