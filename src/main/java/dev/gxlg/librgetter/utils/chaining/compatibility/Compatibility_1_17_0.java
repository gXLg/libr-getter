package dev.gxlg.librgetter.utils.chaining.compatibility;

import dev.gxlg.versiont.gen.de.maxhenkel.tradecycling.TradeCyclingClientMod;
import net.fabricmc.loader.api.FabricLoader;

public class Compatibility_1_17_0 extends Compatibility.Base {
    private final FabricLoader fabricLoader;

    Compatibility_1_17_0() {
        fabricLoader = FabricLoader.getInstance();
    }

    @Override
    public void sendCycleTradesPacket() {
        TradeCyclingClientMod.sendCycleTradesPacket();
    }

    @Override
    public boolean isModPresent(String modID) {
        return fabricLoader.getModContainer(modID).isPresent();
    }
}
