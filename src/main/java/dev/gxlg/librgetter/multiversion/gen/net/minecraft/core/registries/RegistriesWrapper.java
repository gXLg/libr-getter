package dev.gxlg.librgetter.multiversion.gen.net.minecraft.core.registries;

import dev.gxlg.librgetter.multiversion.R;

public class RegistriesWrapper extends R.RWrapper<RegistriesWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_7924/net.minecraft.core.registries.Registries");

    protected RegistriesWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public static RegistriesWrapper inst(Object instance) {
        return new RegistriesWrapper(instance);
    }

    public static dev.gxlg.librgetter.multiversion.gen.net.minecraft.resources.ResourceKeyWrapper ENCHANTMENT() {
        return dev.gxlg.librgetter.multiversion.gen.net.minecraft.resources.ResourceKeyWrapper.inst(clazz.fld("field_41265/ENCHANTMENT").get());
    }
}
