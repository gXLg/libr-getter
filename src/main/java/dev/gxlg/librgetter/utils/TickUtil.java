package dev.gxlg.librgetter.utils;

import dev.gxlg.versiont.gen.net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import dev.gxlg.versiont.gen.net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents$EndLevelTickI;
import dev.gxlg.versiont.gen.net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents$EndTickI;

public class TickUtil {
    // level tick events fire while an actual level is loaded
    // these events don't fire if the game is paused or the player is in the main menu
    // process here: movement, inventory slots, entity interactions
    public static void registerLevelTicker(ClientTickEvents$EndLevelTickI endTick) {
        ClientTickEvents.END_LEVEL_TICK().register(endTick.asClientTickEvents$EndLevelTick());
    }

    // client tick events fire while Minecraft is loaded
    // these events fire always, even when the game is paused
    // process here: keybinds
    public static void registerClientTicker(ClientTickEvents$EndTickI endTick) {
        ClientTickEvents.END_CLIENT_TICK().register(endTick.asClientTickEvents$EndTick());
    }
}
