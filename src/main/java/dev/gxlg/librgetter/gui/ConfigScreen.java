package dev.gxlg.librgetter.gui;

import dev.gxlg.multiversion.R;
import dev.gxlg.multiversion.gen.net.minecraft.client.gui.screens.inventory.BookViewScreenWrapper;

public class ConfigScreen extends BookViewScreenWrapper {
    public static final R.RClass clazz = R.extendWrapper(BookViewScreenWrapper.class, ConfigScreen.class);

    public ConfigScreen() {
        super(clazz, ConfigMenu.getCachedContent());
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

    @Override
    protected void closeScreen() {
        // used only with RUN_COMMAND in 1.21.5 and before
        // by not executing super, we avoid the book actually closing
    }

    public void updateScreen() {
        ConfigMenu.updatePage(currentPage);
        this.setBookAccess(ConfigMenu.updateAndReturnContent());
    }

    private static int currentPage = 0;
}
