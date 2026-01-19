package dev.gxlg.multiversion.gen.net.minecraft.world.item.enchantment;

import dev.gxlg.multiversion.R;

public class EnchantmentWrapper extends R.RWrapper<EnchantmentWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_1887/net.minecraft.world.item.enchantment.Enchantment");

    protected EnchantmentWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public boolean isTradeable(){
        return (boolean) this.instance.mthd("method_25949/isTradeable").invk();
    }

    public static EnchantmentWrapper inst(Object instance) {
        return new EnchantmentWrapper(instance);
    }
}