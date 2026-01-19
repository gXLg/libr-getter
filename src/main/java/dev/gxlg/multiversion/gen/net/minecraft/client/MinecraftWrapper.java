package dev.gxlg.multiversion.gen.net.minecraft.client;

import dev.gxlg.multiversion.R;

public class MinecraftWrapper extends R.RWrapper<MinecraftWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_310/net.minecraft.client.Minecraft");

    protected MinecraftWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public static MinecraftWrapper inst(Object instance) {
        return new MinecraftWrapper(instance);
    }
}