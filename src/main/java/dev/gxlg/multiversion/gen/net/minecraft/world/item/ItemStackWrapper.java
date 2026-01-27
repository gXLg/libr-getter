package dev.gxlg.multiversion.gen.net.minecraft.world.item;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class ItemStackWrapper extends dev.gxlg.multiversion.gen.net.minecraft.core.component.DataComponentHolderWrapper {
    public static final R.RClass clazz = R.clz("net.minecraft.class_1799/net.minecraft.world.item.ItemStack");

    private int superCall = 0;

    protected ItemStackWrapper(Object instance) {
        super(instance);
    }

    public dev.gxlg.multiversion.gen.net.minecraft.nbt.CompoundTagWrapper getTag(){
        return dev.gxlg.multiversion.gen.net.minecraft.nbt.CompoundTagWrapper.inst(clazz.inst(this.instance).mthd("method_7969/getTag", dev.gxlg.multiversion.gen.net.minecraft.nbt.CompoundTagWrapper.clazz).invk());
    }

    public dev.gxlg.multiversion.gen.net.minecraft.world.item.enchantment.ItemEnchantmentsWrapper getEnchantments(){
        return dev.gxlg.multiversion.gen.net.minecraft.world.item.enchantment.ItemEnchantmentsWrapper.inst(clazz.inst(this.instance).mthd("method_58657/getEnchantments", dev.gxlg.multiversion.gen.net.minecraft.world.item.enchantment.ItemEnchantmentsWrapper.clazz).invk());
    }

    public boolean is(dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemWrapper item){
        return (boolean) clazz.inst(this.instance).mthd("method_31574/is", boolean.class, dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemWrapper.clazz).invk(item.unwrap());
    }

    public dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemWrapper getItem(){
        return dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemWrapper.inst(clazz.inst(this.instance).mthd("method_7909/getItem", dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemWrapper.clazz).invk());
    }

    public int getCount(){
        return (int) clazz.inst(this.instance).mthd("method_7947/getCount", int.class).invk();
    }

    public boolean isEmpty(){
        return (boolean) clazz.inst(this.instance).mthd("method_7960/isEmpty", boolean.class).invk();
    }

    public boolean isDamageableItem(){
        return (boolean) clazz.inst(this.instance).mthd("method_7963/isDamageableItem", boolean.class).invk();
    }

    public int getMaxDamage(){
        return (int) clazz.inst(this.instance).mthd("method_7936/getMaxDamage", int.class).invk();
    }

    public int getDamageValue(){
        return (int) clazz.inst(this.instance).mthd("method_7919/getDamageValue", int.class).invk();
    }

    public float getDestroySpeed(dev.gxlg.multiversion.gen.net.minecraft.world.level.block.state.BlockStateWrapper state){
        return (float) clazz.inst(this.instance).mthd("method_7924/getDestroySpeed", float.class, dev.gxlg.multiversion.gen.net.minecraft.world.level.block.state.BlockStateWrapper.clazz).invk(state.unwrap());
    }

    public static ItemStackWrapper inst(Object instance) {
        return instance == null ? null : new ItemStackWrapper(instance);
    }

    public static boolean matches(dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemStackWrapper stackA, dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemStackWrapper stackB){
        return (boolean) clazz.mthd("method_7973/matches", boolean.class, dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemStackWrapper.clazz, dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemStackWrapper.clazz).invk(stackA.unwrap(), stackB.unwrap());
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") ItemStackWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return dev.gxlg.multiversion.gen.net.minecraft.core.component.DataComponentHolderWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}