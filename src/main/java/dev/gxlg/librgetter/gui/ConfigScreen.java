package dev.gxlg.librgetter.gui;

import dev.gxlg.librgetter.mixin.BookScreenAccessor;
import dev.gxlg.librgetter.utils.reflection.ConfigMenu;
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

    public void updateScreen() {
        // update screen
        ((BookScreenAccessor) this).setCachedPageIndex(-1);
        ConfigMenu.updatePage(currentPage);
    }
}
