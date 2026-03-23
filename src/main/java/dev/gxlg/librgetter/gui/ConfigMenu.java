package dev.gxlg.librgetter.gui;

import dev.gxlg.librgetter.utils.chaining.gui.Gui;
import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.config.ConfigManager;
import dev.gxlg.librgetter.utils.types.config.helpers.Configurable;
import dev.gxlg.librgetter.utils.types.exceptions.runtime.InvalidBookIndexException;
import dev.gxlg.librgetter.utils.types.messages.objects.configScreen.ConfigPageContent;
import dev.gxlg.librgetter.utils.types.messages.objects.configScreen.FirstPageContent;
import dev.gxlg.librgetter.utils.types.messages.objects.configScreen.PageContent;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.screens.inventory.BookViewScreen$BookAccess;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigMenu {
    public static final int CONFIGS_PER_PAGE = 4;

    private final int pageCount;

    private final Component[] cachedPageTexts;

    private final List<PageContent> pages;

    public ConfigMenu(String modVersion, ConfigManager configManager) {
        List<ConfigPageContent> configPageList = new ArrayList<>();
        Map<ConfigManager.Category, Integer> categoryIndexMap = new HashMap<>();

        int pageNumber = 1;
        for (ConfigManager.Category category : ConfigManager.Category.values()) {
            categoryIndexMap.put(category, pageNumber);

            List<Configurable<?>> categoryConfigurables = configManager.getConfigurablesForCategory(category);
            int categoryPagesCount = (int) Math.ceil(categoryConfigurables.size() / ((float) CONFIGS_PER_PAGE));
            for (int categoryPageIndex = 0; categoryPageIndex < categoryPagesCount; categoryPageIndex++) {
                int configSliceStart = categoryPageIndex * CONFIGS_PER_PAGE;
                int configSliceEnd = Math.min(configSliceStart + CONFIGS_PER_PAGE, categoryConfigurables.size());
                List<Configurable<?>> configurablesOnPage = categoryConfigurables.subList(configSliceStart, configSliceEnd);
                configPageList.add(new ConfigPageContent(modVersion, category, configurablesOnPage));
            }
            pageNumber += categoryPagesCount;
        }
        pageCount = pageNumber;

        FirstPageContent firstPage = new FirstPageContent(modVersion, categoryIndexMap);
        List<PageContent> pageList = new ArrayList<>();
        pageList.add(firstPage);
        pageList.addAll(configPageList);
        pages = List.copyOf(pageList);

        // pre-fill pages with empty text
        cachedPageTexts = new Component[pageCount];
        Arrays.fill(cachedPageTexts, Texts.literal(""));
    }

    public BookViewScreen$BookAccess createNewBookAccess() {
        for (int pageIndex = 0; pageIndex < pageCount; pageIndex++) {
            updatePageCache(pageIndex);
        }
        return Gui.createBookAccess(Arrays.asList(cachedPageTexts));
    }

    public BookViewScreen$BookAccess getUpdatedBookAccess(int updatePageIndex) {
        updatePageCache(updatePageIndex);
        return Gui.createBookAccess(Arrays.asList(cachedPageTexts));
    }

    private void updatePageCache(int pageIndex) {
        if (pageIndex < 0 || pageIndex >= pageCount) {
            throw new InvalidBookIndexException(pageIndex);
        }
        cachedPageTexts[pageIndex] = pages.get(pageIndex).getComponent();
    }

    public int getPageCount() {
        return pageCount;
    }
}
