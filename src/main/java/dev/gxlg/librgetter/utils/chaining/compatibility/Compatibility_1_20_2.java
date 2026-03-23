package dev.gxlg.librgetter.utils.chaining.compatibility;

import dev.gxlg.versiont.gen.de.maxhenkel.tradecycling.net.CycleTradesPacket;
import dev.gxlg.versiont.gen.net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class Compatibility_1_20_2 extends Compatibility_1_17_0 {
    @Override
    public void sendCycleTradesPacket() {
        ClientPlayNetworking.send(new CycleTradesPacket());
    }
}
