package dev.gxlg.librgetter.multiversion.gen.net.minecraft.resources;

import dev.gxlg.librgetter.multiversion.R;

public class ResourceKeyWrapper extends R.RWrapper<ResourceKeyWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_5321/net.minecraft.resources.ResourceKey");

    protected ResourceKeyWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public static ResourceKeyWrapper inst(Object instance) {
        return new ResourceKeyWrapper(instance);
    }
}
