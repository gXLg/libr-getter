package dev.gxlg.multiversion.gen.net.minecraft.client.gui.screens.inventory;

import dev.gxlg.multiversion.R;

public class BookViewScreen$BookAccessWrapper extends R.RWrapper<BookViewScreen$BookAccessWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_3872$class_3931/net.minecraft.client.gui.screens.inventory.BookViewScreen$BookAccess");

    public BookViewScreen$BookAccessWrapper(java.util.List<dev.gxlg.multiversion.gen.net.minecraft.network.chat.ComponentWrapper> pages){
        this(clazz.constr(java.util.List.class).newInst(dev.gxlg.multiversion.adapters.java.util.ListAdapter.<dev.gxlg.multiversion.gen.net.minecraft.network.chat.ComponentWrapper>unwrapper(dev.gxlg.multiversion.gen.net.minecraft.network.chat.ComponentWrapper::unwrap).apply(pages)).self());
    }

    protected BookViewScreen$BookAccessWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public static BookViewScreen$BookAccessWrapper inst(Object instance) {
        return new BookViewScreen$BookAccessWrapper(instance);
    }
}