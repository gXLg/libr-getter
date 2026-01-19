package dev.gxlg.multiversion.gen.net.minecraft.world.item.enchantment;

import dev.gxlg.multiversion.R;

public class EnchantmentsWrapper extends R.RWrapper<EnchantmentsWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_1893/net.minecraft.world.item.enchantment.Enchantments");

    protected EnchantmentsWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public static EnchantmentsWrapper inst(Object instance) {
        return new EnchantmentsWrapper(instance);
    }

    public static net.minecraft.world.item.enchantment.Enchantment EFFICIENCY() {
        return (net.minecraft.world.item.enchantment.Enchantment) clazz.fld("field_9131/EFFICIENCY").get();
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.resources.ResourceKeyWrapper EFFICIENCY2() {
        return dev.gxlg.multiversion.gen.net.minecraft.resources.ResourceKeyWrapper.inst(clazz.fld("field_9131/EFFICIENCY").get());
    }
}