package dev.gxlg.multiversion.gen.net.minecraft.resources;

import dev.gxlg.multiversion.R;

public class IdentifierWrapper extends R.RWrapper<IdentifierWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_2960/net.minecraft.resources.ResourceLocation/net.minecraft.resources.Identifier");

    protected IdentifierWrapper(Object instance) {
        super(instance);
        R.RInstance rInstance = clazz.inst(instance);
    }

    public static IdentifierWrapper inst(Object instance) {
        return new IdentifierWrapper(instance);
    }

    public static net.minecraft.resources.Identifier tryBuild(String namespace, String path){
        return (net.minecraft.resources.Identifier) clazz.mthd("method_43902/tryBuild", String.class, String.class).invk(namespace, path);
    }
}