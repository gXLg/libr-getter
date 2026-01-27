package dev.gxlg.librgetter.utils.chaining.texts;

import dev.gxlg.multiversion.gen.net.minecraft.network.chat.ClickEvent$ChangePageWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.network.chat.ClickEvent$RunCommandWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.network.chat.ClickEventWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.network.chat.ComponentWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.network.chat.HoverEvent$ShowTextWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.network.chat.HoverEventWrapper;

public class Texts_1_21_5 extends Texts_1_19_4 {
    @Override
    protected ClickEventWrapper runnable(String command) {
        return new ClickEvent$RunCommandWrapper(command);
    }

    @Override
    protected ClickEventWrapper paging(int page) {
        return new ClickEvent$ChangePageWrapper(page);
    }

    @Override
    protected HoverEventWrapper hoverable(ComponentWrapper text) {
        return new HoverEvent$ShowTextWrapper(text);
    }
}
