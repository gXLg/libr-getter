package dev.gxlg.multiversion.gen.net.minecraft.network.protocol.common.custom;

import dev.gxlg.multiversion.R;

public class CustomPacketPayloadWrapper extends R.RWrapper<CustomPacketPayloadWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_8710/net.minecraft.network.protocol.common.custom.CustomPacketPayload");

    protected CustomPacketPayloadWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public static CustomPacketPayloadWrapper inst(Object instance) {
        return new CustomPacketPayloadWrapper(instance);
    }
}