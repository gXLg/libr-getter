package dev.gxlg.multiversion.gen.net.minecraft.core;

import dev.gxlg.multiversion.R;

public class HolderSet$NamedWrapper extends R.RWrapper<HolderSet$NamedWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_6885$class_6888/net.minecraft.core.HolderSet$Named");

    protected HolderSet$NamedWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public java.util.stream.Stream stream(){
        return (java.util.stream.Stream) this.instance.mthd("method_40239/stream").invk();
    }

    public static HolderSet$NamedWrapper inst(Object instance) {
        return new HolderSet$NamedWrapper(instance);
    }
}