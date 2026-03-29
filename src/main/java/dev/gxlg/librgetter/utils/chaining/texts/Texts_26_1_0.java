package dev.gxlg.librgetter.utils.chaining.texts;

import dev.gxlg.versiont.gen.net.minecraft.client.player.LocalPlayer;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Component;

public class Texts_26_1_0 extends Texts_1_21_5 {
    @Override
    protected void sendMessage(LocalPlayer player, Component text, boolean actionbar) {
        if (actionbar) {
            player.sendOverlayMessage(text);
        } else {
            player.sendSystemMessage(text);
        }
    }
}
