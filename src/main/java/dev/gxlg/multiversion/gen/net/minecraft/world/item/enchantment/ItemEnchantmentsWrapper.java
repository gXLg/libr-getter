package dev.gxlg.multiversion.gen.net.minecraft.world.item.enchantment;

import dev.gxlg.multiversion.R;

public class ItemEnchantmentsWrapper extends R.RWrapper<ItemEnchantmentsWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_9304/net.minecraft.world.item.enchantment.ItemEnchantments");

    protected ItemEnchantmentsWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public static ItemEnchantmentsWrapper inst(Object instance) {
        return new ItemEnchantmentsWrapper(instance);
    }
}