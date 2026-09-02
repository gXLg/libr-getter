package dev.gxlg.librgetter.utils;

import dev.gxlg.versiont.gen.net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import dev.gxlg.versiont.gen.net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents$EndLevelTickI;
import dev.gxlg.versiont.gen.net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents$EndTickI;

public class TickUtil {
    public static void registerLevelTicker(ClientTickEvents$EndLevelTickI endTick) {
        ClientTickEvents.END_LEVEL_TICK().register(endTick.asClientTickEvents$EndLevelTick());
    }

    public static void registerClientTicker(ClientTickEvents$EndTickI endTick) {
        ClientTickEvents.END_CLIENT_TICK().register(endTick.asClientTickEvents$EndTick());
    }
}
