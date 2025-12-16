package dev.gxlg.librgetter.utils.reflection.chaining.texts;

import dev.gxlg.librgetter.multiversion.C;
import dev.gxlg.librgetter.multiversion.R;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;

public class Texts_1_21_5 extends Texts_1_19_0 {
    @Override
    protected ClickEvent runnable(String command) {
        return (ClickEvent) R.clz("net.minecraft.class_2558$class_10609/net.minecraft.text.ClickEvent$RunCommand").constr(String.class).newInst(command).self();
    }

    @Override
    protected ClickEvent paging(int page) {
        return (ClickEvent) R.clz("net.minecraft.class_2558$class_10605/net.minecraft.text.ClickEvent$ChangePage").constr(int.class).newInst(page).self();
    }

    @Override
    protected HoverEvent hoverable(Object text) {
        return (HoverEvent) R.clz("net.minecraft.class_2568$class_10613/net.minecraft.text.HoverEvent$ShowText").constr(C.Text).newInst(text).self();
    }
}
