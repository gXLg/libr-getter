package dev.gxlg.multiversion.gen.net.minecraft.world.item;

import dev.gxlg.multiversion.R;

public class ItemStackWrapper extends R.RWrapper<ItemStackWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_1799/net.minecraft.world.item.ItemStack");

    protected ItemStackWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public dev.gxlg.multiversion.gen.net.minecraft.nbt.CompoundTagWrapper getTag(){
        return dev.gxlg.multiversion.gen.net.minecraft.nbt.CompoundTagWrapper.inst(this.instance.mthd("method_7969/getTag").invk());
    }

    public dev.gxlg.multiversion.gen.net.minecraft.world.item.enchantment.ItemEnchantmentsWrapper getEnchantments(){
        return dev.gxlg.multiversion.gen.net.minecraft.world.item.enchantment.ItemEnchantmentsWrapper.inst(this.instance.mthd("method_58657/getEnchantments").invk());
    }

    public static ItemStackWrapper inst(Object instance) {
        return new ItemStackWrapper(instance);
    }
}