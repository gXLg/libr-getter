package dev.gxlg.librgetter.utils.chaining.helper;

import dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.ClientLevelWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.client.player.LocalPlayerWrapper;

public class Helper_1_21_9 extends Helper_1_21_5 {
    @Override
    protected ClientLevelWrapper getWorld(LocalPlayerWrapper player) {
        return player.level();
    }
}
