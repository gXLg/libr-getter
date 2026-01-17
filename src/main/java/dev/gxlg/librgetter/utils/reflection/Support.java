package dev.gxlg.librgetter.utils.reflection;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.multiversion.V;
import dev.gxlg.multiversion.gen.de.maxhenkel.tradecycling.TradeCyclingClientModWrapper;
import dev.gxlg.multiversion.gen.de.maxhenkel.tradecycling.net.CycleTradesPacketWrapper;
import dev.gxlg.multiversion.gen.net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworkingWrapper;
import net.fabricmc.loader.api.FabricLoader;

public class Support {
    private static final FabricLoader fabricLoader;

    private static final boolean tradeCycling;

    static {
        fabricLoader = FabricLoader.getInstance();
        tradeCycling = fabricLoader.getModContainer("trade_cycling").isPresent();
    }

    public static void sendCycleTradesPacket() {
        if (!V.lower("1.20.2")) {
            ClientPlayNetworkingWrapper.send(new CycleTradesPacketWrapper());
        } else {
            TradeCyclingClientModWrapper.sendCycleTradesPacket();
        }
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isUsingTradeCycling() {
        return tradeCycling && LibrGetter.config.tradeCycling;
    }

    public static boolean isModPresent(String modID) {
        return fabricLoader.getModContainer(modID).isPresent();
    }
}
