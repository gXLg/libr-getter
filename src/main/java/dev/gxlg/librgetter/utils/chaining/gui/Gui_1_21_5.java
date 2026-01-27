package dev.gxlg.librgetter.utils.chaining.gui;

import dev.gxlg.multiversion.gen.net.minecraft.client.gui.screens.inventory.BookViewScreen$BookAccessWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.network.chat.ComponentWrapper;

import java.util.List;

public class Gui_1_21_5 extends Gui_1_17_0 {
    @Override
    public BookViewScreen$BookAccessWrapper getBookAccess(List<ComponentWrapper> list) {
        return new BookViewScreen$BookAccessWrapper(list);
    }
}
