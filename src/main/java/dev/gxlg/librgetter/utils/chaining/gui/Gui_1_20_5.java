package dev.gxlg.librgetter.utils.chaining.gui;

import dev.gxlg.versiont.gen.net.minecraft.client.gui.screens.inventory.BookViewScreen$BookAccess;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Component;

import java.util.List;

public class Gui_1_20_5 extends Gui_1_17_0 {
    @Override
    public BookViewScreen$BookAccess createBookAccess(List<Component> list) {
        return new BookViewScreen$BookAccess(list);
    }
}
