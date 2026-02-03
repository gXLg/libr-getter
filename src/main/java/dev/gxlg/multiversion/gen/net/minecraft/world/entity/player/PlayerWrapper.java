package dev.gxlg.multiversion.gen.net.minecraft.world.entity.player;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class PlayerWrapper extends dev.gxlg.multiversion.gen.net.minecraft.world.entity.LivingEntityWrapper {
    public static final R.RClass clazz = R.clz("net.minecraft.class_1657/net.minecraft.world.entity.player.Player");

    private int superCall = 0;

    private final R.RField inventoryMenuField;

    private final R.RField containerMenuField;

    protected PlayerWrapper(Object instance) {
        super(instance);
        R.RInstance rInstance = clazz.inst(instance);
        this.inventoryMenuField = rInstance.fld("field_7498/inventoryMenu", dev.gxlg.multiversion.gen.net.minecraft.world.inventory.InventoryMenuWrapper.clazz.self());
        this.containerMenuField = rInstance.fld("field_7512/containerMenu", dev.gxlg.multiversion.gen.net.minecraft.world.inventory.AbstractContainerMenuWrapper.clazz.self());
    }

    public dev.gxlg.multiversion.gen.net.minecraft.world.entity.player.InventoryWrapper getInventory(){
        return dev.gxlg.multiversion.gen.net.minecraft.world.entity.player.InventoryWrapper.inst(clazz.inst(this.instance).mthd("method_31548/getInventory", dev.gxlg.multiversion.gen.net.minecraft.world.entity.player.InventoryWrapper.clazz.self()).invk());
    }

    public dev.gxlg.multiversion.gen.net.minecraft.world.inventory.InventoryMenuWrapper getInventoryMenuField() {
        return dev.gxlg.multiversion.gen.net.minecraft.world.inventory.InventoryMenuWrapper.inst(this.inventoryMenuField.get());
    }

    public void setInventoryMenuField(dev.gxlg.multiversion.gen.net.minecraft.world.inventory.InventoryMenuWrapper value) {
        this.inventoryMenuField.set(value.unwrap());
    }

    public dev.gxlg.multiversion.gen.net.minecraft.world.inventory.AbstractContainerMenuWrapper getContainerMenuField() {
        return dev.gxlg.multiversion.gen.net.minecraft.world.inventory.AbstractContainerMenuWrapper.inst(this.containerMenuField.get());
    }

    public void setContainerMenuField(dev.gxlg.multiversion.gen.net.minecraft.world.inventory.AbstractContainerMenuWrapper value) {
        this.containerMenuField.set(value.unwrap());
    }

    public static PlayerWrapper inst(Object instance) {
        return instance == null ? null : new PlayerWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") PlayerWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return dev.gxlg.multiversion.gen.net.minecraft.world.entity.LivingEntityWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}