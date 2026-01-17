package dev.gxlg.multiversion.gen.net.minecraft.client;

import dev.gxlg.multiversion.R;

public class KeyMappingWrapper extends R.RWrapper<KeyMappingWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_304/net.minecraft.client.KeyMapping");

    public KeyMappingWrapper(String id, com.mojang.blaze3d.platform.InputConstants.Type type, int keyCode, dev.gxlg.multiversion.gen.net.minecraft.client.KeyMapping$CategoryWrapper category){
        this(clazz.constr(String.class, com.mojang.blaze3d.platform.InputConstants.Type.class, int.class, dev.gxlg.multiversion.gen.net.minecraft.client.KeyMapping$CategoryWrapper.clazz).newInst(id, type, keyCode, category.unwrap()).self());
    }

    public KeyMappingWrapper(String id, com.mojang.blaze3d.platform.InputConstants.Type type, int keyCode, String categoryId){
        this(clazz.constr(String.class, com.mojang.blaze3d.platform.InputConstants.Type.class, int.class, String.class).newInst(id, type, keyCode, categoryId).self());
    }

    protected KeyMappingWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public static KeyMappingWrapper inst(Object instance) {
        return new KeyMappingWrapper(instance);
    }
}