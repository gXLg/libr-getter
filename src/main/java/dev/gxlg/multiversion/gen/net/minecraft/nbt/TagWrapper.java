package dev.gxlg.multiversion.gen.net.minecraft.nbt;

import dev.gxlg.multiversion.R;

public class TagWrapper extends R.RWrapper<TagWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_2520/net.minecraft.nbt.Tag");

    protected TagWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public byte getId(){
        return (byte) this.instance.mthd("method_10711/getId").invk();
    }

    public static TagWrapper inst(Object instance) {
        return new TagWrapper(instance);
    }
}