package dev.gxlg.librgetter.gui;

import dev.gxlg.librgetter.utils.reflection.ConfigMenu;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;

public class ConfigScreen extends BookViewScreen {
    public ConfigScreen() {
        super(ConfigMenu.getCachedContent());
    }

    @Override
    protected void init() {
        super.init();
        forcePage(currentPage);
    }

    @Override
    protected boolean forcePage(int page) {
        currentPage = page;
        return super.forcePage(page);
    }

    @Override
    protected void pageBack() {
        if (currentPage > 0) {
            currentPage--;
        }
        super.pageBack();
    }

    @Override
    protected void pageForward() {
        if (currentPage < ConfigMenu.pageCount - 1) {
            currentPage++;
        }
        super.pageForward();
    }

    public void updateScreen() {
        ConfigMenu.updatePage(currentPage);
        this.setBookAccess(ConfigMenu.updateAndReturnContent());
    }

    private static int currentPage = 0;
}
