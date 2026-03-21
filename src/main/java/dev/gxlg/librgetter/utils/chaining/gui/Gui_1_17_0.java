package dev.gxlg.librgetter.utils.chaining.gui;

import dev.gxlg.versiont.gen.net.minecraft.client.gui.screens.inventory.BookViewScreen$BookAccess;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.screens.inventory.BookViewScreen$BookAccessI;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Component;

import java.util.List;

public class Gui_1_17_0 extends Gui.Base {
    @Override
    public BookViewScreen$BookAccess createBookAccess(List<Component> list) {
        int pageCount = list.size();
        return new BookViewScreen$BookAccessI() {
            @Override
            public int getPageCount() {
                return pageCount;
            }

            @Override
            public Component getPage(int index) {
                if (index < 0 || index >= pageCount) {
                    return Component.nullToEmpty("");
                }
                return list.get(index);
            }
        }.asBookViewScreen$BookAccess();
    }
}
