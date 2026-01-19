package dev.gxlg.multiversion.gen.net.minecraft.world.item.enchantment;

import dev.gxlg.multiversion.R;

public class EnchantmentHelperWrapper extends R.RWrapper<EnchantmentHelperWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_1890/net.minecraft.world.item.enchantment.EnchantmentHelper");

    protected EnchantmentHelperWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public static EnchantmentHelperWrapper inst(Object instance) {
        return new EnchantmentHelperWrapper(instance);
    }

    public static int getItemEnchantmentLevel(net.minecraft.world.item.enchantment.Enchantment enchantment, net.minecraft.world.item.ItemStack stack){
        return (int) clazz.mthd("method_8225/getItemEnchantmentLevel", net.minecraft.world.item.enchantment.Enchantment.class, net.minecraft.world.item.ItemStack.class).invk(enchantment, stack);
    }
}