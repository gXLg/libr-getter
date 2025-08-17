package com.gxlg.librgetter.utils.reflection;

import com.gxlg.librgetter.LibrGetter;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.packet.CustomPayload;

public class Support {
    private static final boolean tradeCycling;

    static {
        FabricLoader instance = FabricLoader.getInstance();
        tradeCycling = instance.getModContainer("trade_cycling").isPresent();
    }

    public static void sendCycleTradesPacket() {
        Class<?> c = Reflection.clazz("de.maxhenkel.tradecycling.net.CycleTradesPacket");
        ClientPlayNetworking.send((CustomPayload) Reflection.construct(c, null));
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean useTradeCycling() {
        return tradeCycling && LibrGetter.config._tradeCycling;
    }
}
