package dev.gxlg.librgetter.utils.chaining.players;

import dev.gxlg.versiont.gen.net.minecraft.client.multiplayer.ClientLevel;
import dev.gxlg.versiont.gen.net.minecraft.client.player.LocalPlayer;

public class Players_1_21_9 extends Players_1_21_5 {
    @Override
    protected ClientLevel getWorld(LocalPlayer player) {
        return (ClientLevel) player.level();
    }
}
