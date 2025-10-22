package dev.gxlg.librgetter.utils.reflection;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.Reflection;
import net.fabricmc.loader.api.FabricLoader;

public class Support {
    private static final boolean tradeCycling;

    static {
        FabricLoader instance = FabricLoader.getInstance();
        tradeCycling = instance.getModContainer("trade_cycling").isPresent();
    }

    public static void sendCycleTradesPacket() {
        if (Reflection.version(">= 1.20.2")) {
            Reflection.wrapi("[net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking] send§m [.class_8710/.network.packet.CustomPayload]:[de.maxhenkel.tradecycling.net.CycleTradesPacket <>]");
        } else {
            Reflection.wrapi("[de.maxhenkel.tradecycling.TradeCyclingClientMod] sendCycleTradesPacket§m");
        }
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean useTradeCycling() {
        return tradeCycling && LibrGetter.config._tradeCycling;
    }
}
