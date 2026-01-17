package dev.gxlg.multiversion.gen.net.minecraft.core;

import dev.gxlg.multiversion.R;

public class Holder$ReferenceWrapper extends R.RWrapper<Holder$ReferenceWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_6880$class_6883/net.minecraft.core.Holder$Reference");

    protected Holder$ReferenceWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public Object value(){
        return this.instance.mthd("comp_349/value").invk();
    }

    public static Holder$ReferenceWrapper inst(Object instance) {
        return new Holder$ReferenceWrapper(instance);
    }
}