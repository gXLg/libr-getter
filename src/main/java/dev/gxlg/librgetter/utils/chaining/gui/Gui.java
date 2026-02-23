package dev.gxlg.librgetter.utils.chaining.gui;

import dev.gxlg.versiont.api.V;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.screens.inventory.BookViewScreen$BookAccess;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Component;

import java.util.List;

public class Gui {
    private static Base implementation = null;

    public static BookViewScreen$BookAccess createBookAccess(List<Component> list) {
        return getImpl().createBookAccess(list);
    }

    private static Base getImpl() {
        if (implementation != null) {
            return implementation;
        }
        if (V.lower("1.21.5")) {
            implementation = new Gui_1_17_0();
        } else {
            implementation = new Gui_1_21_5();
        }
        return implementation;
    }

    public abstract static class Base {
        public abstract BookViewScreen$BookAccess createBookAccess(List<Component> list);
    }
}
