package dev.gxlg.librgetter.utils.chaining.support;

import dev.gxlg.multiversion.gen.de.maxhenkel.tradecycling.net.CycleTradesPacketWrapper;
import dev.gxlg.multiversion.gen.net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworkingWrapper;

public class Support_1_20_2 extends Support_1_17_0 {
    @Override
    public void sendCycleTradesPacket() {
        ClientPlayNetworkingWrapper.send(new CycleTradesPacketWrapper());
    }
}
