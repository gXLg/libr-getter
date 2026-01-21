package dev.gxlg.librgetter.utils.chaining.helper;

import dev.gxlg.multiversion.gen.net.minecraft.client.player.LocalPlayerWrapper;
import net.minecraft.client.multiplayer.ClientLevel;

public class Helper_1_21_9 extends Helper_1_21_5 {
    @Override
    protected ClientLevel getWorld(LocalPlayerWrapper player) {
        return LocalPlayerWrapper.inst(player).level();
    }
}
