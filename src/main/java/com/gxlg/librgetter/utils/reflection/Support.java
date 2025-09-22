package com.gxlg.librgetter.utils.reflection;

import com.gxlg.librgetter.LibrGetter;
import com.gxlg.librgetter.utils.MultiVersion;
import net.fabricmc.loader.api.FabricLoader;

public class Support {
    private static final boolean tradeCycling;

    static {
        FabricLoader instance = FabricLoader.getInstance();
        tradeCycling = instance.getModContainer("trade_cycling").isPresent();
    }

    public static void sendCycleTradesPacket() {
        if (MultiVersion.isApiLevel(MultiVersion.ApiLevel.CUSTOM_PAYLOAD)) {
            Class<?> c = Reflection.clazz("de.maxhenkel.tradecycling.net.CycleTradesPacket");
            Class<?> n = Reflection.clazz("net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking");
            Class<?> l = Reflection.clazz("net.minecraft.class_8710", "net.minecraft.network.packet.CustomPayload");
            Object p = Reflection.construct(c, null);
            Reflection.invokeMethod(n, null, new Object[]{p}, new Class[]{l}, "send");
        } else {
            Class<?> c = Reflection.clazz("de.maxhenkel.tradecycling.TradeCyclingClientMod");
            Reflection.invokeMethod(c, null, null, "sendCycleTradesPacket");
        }
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean useTradeCycling() {
        return tradeCycling && LibrGetter.config._tradeCycling;
    }
}
