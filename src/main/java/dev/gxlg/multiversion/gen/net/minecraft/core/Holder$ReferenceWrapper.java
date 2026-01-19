package dev.gxlg.multiversion.gen.net.minecraft.core;

import dev.gxlg.multiversion.R;

public class Holder$ReferenceWrapper extends dev.gxlg.multiversion.gen.net.minecraft.core.HolderWrapper {
    public static final R.RClass clazz = R.clz("net.minecraft.class_6880$class_6883/net.minecraft.core.Holder$Reference");

    protected Holder$ReferenceWrapper(Object instance) {
        super(instance);
    }

    public dev.gxlg.multiversion.gen.net.minecraft.resources.ResourceKeyWrapper key(){
        return dev.gxlg.multiversion.gen.net.minecraft.resources.ResourceKeyWrapper.inst(this.instance.mthd("method_40237/key").invk());
    }

    public Object value(){
        return this.instance.mthd("comp_349/value").invk();
    }

    public static Holder$ReferenceWrapper inst(Object instance) {
        return new Holder$ReferenceWrapper(instance);
    }
}