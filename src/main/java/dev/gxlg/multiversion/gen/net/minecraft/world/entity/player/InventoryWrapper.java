package dev.gxlg.multiversion.gen.net.minecraft.world.entity.player;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class InventoryWrapper extends dev.gxlg.multiversion.gen.net.minecraft.world.ContainerWrapper {
    public static final R.RClass clazz = R.clz("net.minecraft.class_1661/net.minecraft.world.entity.player.Inventory");

    private int superCall = 0;

    private final R.RField selectedField;

    protected InventoryWrapper(Object instance) {
        super(instance);
        R.RInstance rInstance = clazz.inst(instance);
        this.selectedField = rInstance.fld("field_7545/selected", int.class);
    }

    public void setSelectedSlot(int slot){
        clazz.inst(this.instance).mthd("method_61496/setSelectedSlot", void.class, int.class).invk(slot);
    }

    public int getSelectedField() {
        return (int) this.selectedField.get();
    }

    public void setSelectedField(int value) {
        this.selectedField.set(value);
    }

    public int getSuitableHotbarSlot(){
        return (int) clazz.inst(this.instance).mthd("method_7386/getSuitableHotbarSlot", int.class).invk();
    }

    public int findSlotMatchingItem(dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemStackWrapper stack){
        return (int) clazz.inst(this.instance).mthd("method_7395/findSlotMatchingItem", int.class, dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemStackWrapper.clazz).invk(stack.unwrap());
    }

    public static InventoryWrapper inst(Object instance) {
        return instance == null ? null : new InventoryWrapper(instance);
    }

    public static boolean isHotbarSlot(int slot){
        return (boolean) clazz.mthd("method_7380/isHotbarSlot", boolean.class, int.class).invk(slot);
    }

    public static int SLOT_OFFHAND() {
        return (int) clazz.fld("field_30639/SLOT_OFFHAND", int.class).get();
    }

    public static int INVENTORY_SIZE() {
        return (int) clazz.fld("field_30638/INVENTORY_SIZE", int.class).get();
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") InventoryWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return dev.gxlg.multiversion.gen.net.minecraft.world.ContainerWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}