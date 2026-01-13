package dev.gxlg.librgetter.multiversion.gen.net.minecraft.core.registries;

import dev.gxlg.librgetter.multiversion.R;

public class BuiltInRegistriesWrapper extends R.RWrapper<BuiltInRegistriesWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_7923/net.minecraft.core.registries.BuiltInRegistries");

    protected BuiltInRegistriesWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public static BuiltInRegistriesWrapper inst(Object instance) {
        return new BuiltInRegistriesWrapper(instance);
    }

    public static dev.gxlg.librgetter.multiversion.gen.net.minecraft.core.RegistryWrapper ENCHANTMENT() {
        return dev.gxlg.librgetter.multiversion.gen.net.minecraft.core.RegistryWrapper.inst(clazz.fld("field_41176/ENCHANTMENT").get());
    }
}
