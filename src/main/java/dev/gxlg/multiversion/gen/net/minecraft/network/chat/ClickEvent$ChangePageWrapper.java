package dev.gxlg.multiversion.gen.net.minecraft.network.chat;

import dev.gxlg.multiversion.R;

public class ClickEvent$ChangePageWrapper extends R.RWrapper<ClickEvent$ChangePageWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_2558$class_10605/net.minecraft.network.chat.ClickEvent$ChangePage");

    public ClickEvent$ChangePageWrapper(int page){
        this(clazz.constr(int.class).newInst(page).self());
    }

    protected ClickEvent$ChangePageWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public static ClickEvent$ChangePageWrapper inst(Object instance) {
        return new ClickEvent$ChangePageWrapper(instance);
    }
}