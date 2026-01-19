package dev.gxlg.multiversion.gen.net.minecraft.core;

import dev.gxlg.multiversion.R;

public class HolderWrapper extends R.RWrapper<HolderWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_6880/net.minecraft.core.Holder");

    protected HolderWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public boolean is(dev.gxlg.multiversion.gen.net.minecraft.resources.ResourceKeyWrapper resourceKey){
        return (boolean) this.instance.mthd("method_40225/is", dev.gxlg.multiversion.gen.net.minecraft.resources.ResourceKeyWrapper.clazz).invk(resourceKey.unwrap());
    }

    public boolean is(dev.gxlg.multiversion.gen.net.minecraft.tags.TagKeyWrapper resourceKey){
        return (boolean) this.instance.mthd("method_40220/is", dev.gxlg.multiversion.gen.net.minecraft.tags.TagKeyWrapper.clazz).invk(resourceKey.unwrap());
    }

    public String getRegisteredName(){
        return (String) this.instance.mthd("method_55840/getRegisteredName").invk();
    }

    public static HolderWrapper inst(Object instance) {
        return new HolderWrapper(instance);
    }
}