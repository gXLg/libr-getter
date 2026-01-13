package dev.gxlg.librgetter.gui;

import dev.gxlg.librgetter.mixin.BookViewScreenAccessor;
import dev.gxlg.librgetter.utils.reflection.ConfigMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;

public class ConfigScreen extends BookViewScreen {
    public ConfigScreen() {
        super(CONTENT);
    }

    @Override
    protected void init() {
        super.init();
        forcePage(currentPage);
    }

    @Override
    protected boolean forcePage(int page) {
        currentPage = page;
        updateScreen();
        return super.forcePage(page);
    }

    @Override
    protected void pageBack() {
        if (currentPage > 0) {
            currentPage--;
        }
        updateScreen();
        super.pageBack();
    }

    @Override
    protected void pageForward() {
        if (currentPage < ConfigMenu.pageCount - 1) {
            currentPage++;
        }
        updateScreen();
        super.pageForward();
    }

    public void updateScreen() {
        ConfigMenu.updatePage(currentPage);
        ((BookViewScreenAccessor) this).setCachedPage(-1);
    }

    private static final BookAccess CONTENT = ConfigMenu.getContent();

    private static int currentPage = 0;

    public static boolean configChange() {
        if (Minecraft.getInstance().screen instanceof ConfigScreen cs) {
            cs.updateScreen();
            return true;
        }
        return false;
    }
}
