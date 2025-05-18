package com.gxlg.librgetter.utils.reflection;

import com.gxlg.librgetter.LibrGetter;
import net.fabricmc.loader.api.FabricLoader;

public class Support {
    private static final boolean tradeCycling;

    static {
        FabricLoader instance = FabricLoader.getInstance();
        tradeCycling = instance.getModContainer("trade_cycling").isPresent();
    }

    public static void sendCycleTradesPacket() {
        Class<?> c = Reflection.clazz("de.maxhenkel.tradecycling.FabricTradeCyclingClientMod");
        Object i = Reflection.invokeMethod(c, null, null, "instance");
        Reflection.invokeMethod(c, i, null, "sendCycleTradesPacket");
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean useTradeCycling() {
        return tradeCycling && LibrGetter.config._tradeCycling;
    }
}
