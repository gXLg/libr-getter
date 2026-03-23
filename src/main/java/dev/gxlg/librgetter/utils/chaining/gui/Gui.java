package dev.gxlg.librgetter.utils.chaining.gui;

import dev.gxlg.versiont.api.V;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.screens.inventory.BookViewScreen$BookAccess;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Component;

import java.util.List;

public class Gui {
    private static final Base implementation;

    static {
        if (V.lower("1.21.5")) {
            implementation = new Gui_1_17_0();
        } else {
            implementation = new Gui_1_21_5();
        }
    }

    public static BookViewScreen$BookAccess createBookAccess(List<Component> list) {
        return implementation.createBookAccess(list);
    }

    public abstract static class Base {
        public abstract BookViewScreen$BookAccess createBookAccess(List<Component> list);
    }
}
