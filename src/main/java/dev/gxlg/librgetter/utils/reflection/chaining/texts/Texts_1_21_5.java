package dev.gxlg.librgetter.utils.reflection.chaining.texts;

import dev.gxlg.multiversion.gen.net.minecraft.network.chat.ClickEvent$ChangePageWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.network.chat.ClickEvent$RunCommandWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.network.chat.ComponentWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.network.chat.HoverEvent$ShowTextWrapper;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.HoverEvent;

public class Texts_1_21_5 extends Texts_1_19_0 {
    @Override
    protected ClickEvent runnable(String command) {
        return new ClickEvent$RunCommandWrapper(command).unwrap(ClickEvent.class);
    }

    @Override
    protected ClickEvent paging(int page) {
        return new ClickEvent$ChangePageWrapper(page).unwrap(ClickEvent.class);
    }

    @Override
    protected HoverEvent hoverable(ComponentWrapper text) {
        return new HoverEvent$ShowTextWrapper(text).unwrap(HoverEvent.class);
    }
}
