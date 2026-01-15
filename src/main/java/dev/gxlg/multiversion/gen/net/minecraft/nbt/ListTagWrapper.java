package dev.gxlg.multiversion.gen.net.minecraft.nbt;

import dev.gxlg.multiversion.R;

public class ListTagWrapper extends R.RWrapper<ListTagWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_2499/net.minecraft.nbt.ListTag");

    protected ListTagWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public static ListTagWrapper inst(Object instance) {
        return new ListTagWrapper(instance);
    }
}