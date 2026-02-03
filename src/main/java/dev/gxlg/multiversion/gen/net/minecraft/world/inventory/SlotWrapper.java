package dev.gxlg.multiversion.gen.net.minecraft.world.inventory;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class SlotWrapper extends R.RWrapper<SlotWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_1735/net.minecraft.world.inventory.Slot");

    private int superCall = 0;

    private final R.RField containerField;

    protected SlotWrapper(Object instance) {
        super(instance);
        R.RInstance rInstance = clazz.inst(instance);
        this.containerField = rInstance.fld("field_7871/container", dev.gxlg.multiversion.gen.net.minecraft.world.ContainerWrapper.clazz.self());
    }

    public dev.gxlg.multiversion.gen.net.minecraft.world.ContainerWrapper getContainerField() {
        return dev.gxlg.multiversion.gen.net.minecraft.world.ContainerWrapper.inst(this.containerField.get());
    }

    public void setContainerField(dev.gxlg.multiversion.gen.net.minecraft.world.ContainerWrapper value) {
        this.containerField.set(value.unwrap());
    }

    public static SlotWrapper inst(Object instance) {
        return instance == null ? null : new SlotWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") SlotWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}