package dev.gxlg.librgetter.utils.reflection;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.multiversion.R;
import dev.gxlg.librgetter.multiversion.V;
import net.fabricmc.loader.api.FabricLoader;

public class Support {
    private static final FabricLoader instance;
    private static final boolean tradeCycling;

    static {
        instance = FabricLoader.getInstance();
        tradeCycling = instance.getModContainer("trade_cycling").isPresent();
    }

    public static void sendCycleTradesPacket() {
        if (!V.lower("1.20.2")) {
            R.clz("net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking").mthd("send", R.clz("net.minecraft.class_8710/net.minecraft.network.packet.CustomPayload")).invk(R.clz("de.maxhenkel.tradecycling.net.CycleTradesPacket").constr().newInst().self());
        } else {
            R.clz("de.maxhenkel.tradecycling.TradeCyclingClientMod").mthd("sendCycleTradesPacket").invk();
        }
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean useTradeCycling() {
        return tradeCycling && LibrGetter.config.tradeCycling;
    }

    public static boolean isEffective(String modID) {
        return instance.getModContainer(modID).isPresent();
    }
}
