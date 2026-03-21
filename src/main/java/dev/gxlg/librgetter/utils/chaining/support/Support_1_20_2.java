package dev.gxlg.librgetter.utils.chaining.support;

import dev.gxlg.versiont.gen.de.maxhenkel.tradecycling.net.CycleTradesPacket;
import dev.gxlg.versiont.gen.net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class Support_1_20_2 extends Support_1_17_0 {
    @Override
    public void sendCycleTradesPacket() {
        ClientPlayNetworking.send(new CycleTradesPacket());
    }
}
