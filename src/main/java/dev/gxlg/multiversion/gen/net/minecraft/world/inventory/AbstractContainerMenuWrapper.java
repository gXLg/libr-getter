package dev.gxlg.multiversion.gen.net.minecraft.world.inventory;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class AbstractContainerMenuWrapper extends R.RWrapper<AbstractContainerMenuWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_1703/net.minecraft.world.inventory.AbstractContainerMenu");

    private int superCall = 0;

    private final R.RField containerIdField;

    protected AbstractContainerMenuWrapper(Object instance) {
        super(instance);
        R.RInstance rInstance = clazz.inst(instance);
        this.containerIdField = rInstance.fld("field_7763/containerId", int.class);
    }

    public int getContainerIdField() {
        return (int) this.containerIdField.get();
    }

    public void setContainerIdField(int value) {
        this.containerIdField.set(value);
    }

    public dev.gxlg.multiversion.gen.net.minecraft.world.inventory.SlotWrapper getSlot(int slotId){
        return dev.gxlg.multiversion.gen.net.minecraft.world.inventory.SlotWrapper.inst(clazz.inst(this.instance).mthd("method_7611/getSlot", dev.gxlg.multiversion.gen.net.minecraft.world.inventory.SlotWrapper.clazz.self(), int.class).invk(slotId));
    }

    public static AbstractContainerMenuWrapper inst(Object instance) {
        return instance == null ? null : new AbstractContainerMenuWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") AbstractContainerMenuWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}