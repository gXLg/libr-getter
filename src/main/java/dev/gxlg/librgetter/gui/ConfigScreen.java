package dev.gxlg.librgetter.gui;

import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.screens.inventory.BookViewScreen;

public class ConfigScreen extends BookViewScreen {
    public static final R.RClass clazz = R.extendWrapper(BookViewScreen.class, ConfigScreen.class);

    private final ConfigMenu configMenu;

    public ConfigScreen(ConfigMenu configMenu) {
        super(configMenu.createBookAccess());
        this.configMenu = configMenu;
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
        if (currentPage < configMenu.getPageCount() - 1) {
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
        setBookAccess(configMenu.createBookAccess());
    }

    private static int currentPage = 0;
}
