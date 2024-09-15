package com.gxlg.librgetter.gui;

import com.gxlg.librgetter.mixin.BookScreenAccessor;
import com.gxlg.librgetter.utils.ConfigMenu;
import net.minecraft.client.gui.screen.ingame.BookScreen;

@SuppressWarnings("ReferenceToMixin")
public class ConfigScreen extends BookScreen {
    private static int currentPage = 0;

    private static final Contents CONTENT = ConfigMenu.getContent();

    public ConfigScreen() {
        super(CONTENT);
    }

    @Override
    protected void init() {
        super.init();
        jumpToPage(currentPage);
    }

    @Override
    protected boolean jumpToPage(int page) {
        currentPage = page;
        return super.jumpToPage(page);
    }

    @Override
    protected void goToPreviousPage() {
        if (currentPage > 0) currentPage--;
        super.goToPreviousPage();
    }

    @Override
    protected void goToNextPage() {
        if (currentPage < ConfigMenu.pageCount - 1) currentPage++;
        super.goToNextPage();
    }

    @Override
    protected void closeScreen() { // used only with RUN_COMMAND
        // update screen
        ((BookScreenAccessor) this).setCachedPageIndex(-1);
        ConfigMenu.updatePage(currentPage);
        // by not calling super we avoid the book actually closing
    }
}
