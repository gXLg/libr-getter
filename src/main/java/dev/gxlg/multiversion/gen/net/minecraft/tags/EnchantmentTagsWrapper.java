package dev.gxlg.multiversion.gen.net.minecraft.tags;

import dev.gxlg.multiversion.R;

public class EnchantmentTagsWrapper extends R.RWrapper<EnchantmentTagsWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_9636/net.minecraft.tags.EnchantmentTags");

    protected EnchantmentTagsWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public static EnchantmentTagsWrapper inst(Object instance) {
        return new EnchantmentTagsWrapper(instance);
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.tags.TagKeyWrapper TRADEABLE() {
        return dev.gxlg.multiversion.gen.net.minecraft.tags.TagKeyWrapper.inst(clazz.fld("field_51545/TRADEABLE").get());
    }
}