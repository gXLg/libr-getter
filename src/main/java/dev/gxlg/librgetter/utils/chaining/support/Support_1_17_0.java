package dev.gxlg.librgetter.utils.chaining.support;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.versiont.gen.de.maxhenkel.tradecycling.TradeCyclingClientMod;
import net.fabricmc.loader.api.FabricLoader;

public class Support_1_17_0 extends Support.Base {
    private final FabricLoader fabricLoader;

    private final boolean tradeCycling;

    Support_1_17_0() {
        fabricLoader = FabricLoader.getInstance();
        tradeCycling = fabricLoader.getModContainer("trade_cycling").isPresent();
    }

    @Override
    public void sendCycleTradesPacket() {
        TradeCyclingClientMod.sendCycleTradesPacket();
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    @Override
    public boolean isUsingTradeCycling() {
        return tradeCycling && LibrGetter.config.tradeCycling;
    }

    @Override
    public boolean isModPresent(String modID) {
        return fabricLoader.getModContainer(modID).isPresent();
    }
}
