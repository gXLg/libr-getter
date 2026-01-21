package dev.gxlg.librgetter.utils.chaining.gui;

import dev.gxlg.multiversion.V;
import dev.gxlg.multiversion.gen.net.minecraft.network.chat.ComponentWrapper;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;

import java.util.List;

public abstract class Gui {
    public abstract BookViewScreen.BookAccess getBookAccess(List<ComponentWrapper> list);

    private static Gui implementation = null;

    public static Gui getImpl() {
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
}
