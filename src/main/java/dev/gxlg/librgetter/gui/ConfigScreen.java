package dev.gxlg.librgetter.gui;

import dev.gxlg.librgetter.mixin.BookScreenAccessor;
import dev.gxlg.librgetter.utils.reflection.ConfigMenu;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.BookScreen;

public class ConfigScreen extends BookScreen {
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
        updateScreen();
        return super.jumpToPage(page);
    }

    @Override
    protected void goToPreviousPage() {
        if (currentPage > 0) {
            currentPage--;
        }
        updateScreen();
        super.goToPreviousPage();
    }

    @Override
    protected void goToNextPage() {
        if (currentPage < ConfigMenu.pageCount - 1) {
            currentPage++;
        }
        updateScreen();
        super.goToNextPage();
    }

    public void updateScreen() {
        ConfigMenu.updatePage(currentPage);
        ((BookScreenAccessor) this).setCachedPageIndex(-1);
    }

    private static final Contents CONTENT = ConfigMenu.getContent();

    private static int currentPage = 0;

    public static boolean configChange() {
        if (MinecraftClient.getInstance().currentScreen instanceof ConfigScreen cs) {
            cs.updateScreen();
            return true;
        }
        return false;
    }
}
