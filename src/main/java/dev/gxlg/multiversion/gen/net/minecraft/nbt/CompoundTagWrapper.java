package dev.gxlg.multiversion.gen.net.minecraft.nbt;

import dev.gxlg.multiversion.R;

public class CompoundTagWrapper extends R.RWrapper<CompoundTagWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_2487/net.minecraft.nbt.CompoundTag");

    protected CompoundTagWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public static CompoundTagWrapper inst(Object instance) {
        return new CompoundTagWrapper(instance);
    }
}