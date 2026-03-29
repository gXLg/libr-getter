package dev.gxlg.librgetter.utils.chaining.texts;

import dev.gxlg.versiont.gen.net.minecraft.network.chat.ClickEvent;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.ClickEvent$ChangePage;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.ClickEvent$RunCommand;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Component;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.HoverEvent;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.HoverEvent$ShowText;

public class Texts_1_21_5 extends Texts_1_19_4 {
    @Override
    public ClickEvent runnable(String command) {
        return new ClickEvent$RunCommand(command);
    }

    @Override
    public ClickEvent paging(int page) {
        return new ClickEvent$ChangePage(page);
    }

    @Override
    public HoverEvent hoverable(Component text) {
        return new HoverEvent$ShowText(text);
    }
}
