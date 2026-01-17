package dev.gxlg.multiversion.gen.net.minecraft.client;

import dev.gxlg.multiversion.R;

public class KeyMapping$CategoryWrapper extends R.RWrapper<KeyMapping$CategoryWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_304$class_11900/net.minecraft.client.KeyMapping$Category");

    protected KeyMapping$CategoryWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public static KeyMapping$CategoryWrapper inst(Object instance) {
        return new KeyMapping$CategoryWrapper(instance);
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.client.KeyMapping$CategoryWrapper register(dev.gxlg.multiversion.gen.net.minecraft.resources.IdentifierWrapper id){
        return dev.gxlg.multiversion.gen.net.minecraft.client.KeyMapping$CategoryWrapper.inst(clazz.mthd("method_74698/register", dev.gxlg.multiversion.gen.net.minecraft.resources.IdentifierWrapper.clazz).invk(id.unwrap()));
    }
}