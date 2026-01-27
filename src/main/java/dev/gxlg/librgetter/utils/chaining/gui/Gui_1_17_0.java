package dev.gxlg.librgetter.utils.chaining.gui;

import dev.gxlg.multiversion.gen.net.minecraft.client.gui.screens.inventory.BookViewScreen$BookAccessWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.client.gui.screens.inventory.BookViewScreen$BookAccessWrapperInterface;
import dev.gxlg.multiversion.gen.net.minecraft.network.chat.ComponentWrapper;

import java.util.List;

public class Gui_1_17_0 extends Gui {
    @Override
    public BookViewScreen$BookAccessWrapper getBookAccess(List<ComponentWrapper> list) {
        int pageCount = list.size();
        return new BookViewScreen$BookAccessWrapperInterface() {
            @Override
            public int getPageCount() {
                return pageCount;
            }

            @Override
            public ComponentWrapper getPage(int index) {
                if (index < 0 || index >= pageCount) {
                    return ComponentWrapper.nullToEmpty("");
                }
                return list.get(index);
            }
        }.wrapper();
    }
}
