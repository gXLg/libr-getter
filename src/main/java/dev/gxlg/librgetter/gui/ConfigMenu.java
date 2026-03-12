package dev.gxlg.librgetter.gui;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.chaining.gui.Gui;
import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.config.ConfigManager;
import dev.gxlg.librgetter.utils.types.config.helpers.Configurable;
import dev.gxlg.librgetter.utils.types.exceptions.runtime.InvalidBookIndexException;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.screens.inventory.BookViewScreen$BookAccess;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Component;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.MutableComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigMenu {
    public static final int CONFIGS_PER_PAGE = 4;

    public static final int pageCount;

    private static final Component[] cachedPageTexts;

    private static final Map<ConfigManager.Category, Integer> categoryPageIndices;

    private static final List<Page> pages;

    static {
        List<Page> pageList = new ArrayList<>();
        Map<ConfigManager.Category, Integer> pageIndices = new HashMap<>();

        int pageNumber = 1;
        for (ConfigManager.Category category : ConfigManager.Category.values()) {
            pageIndices.put(category, pageNumber);

            List<Configurable<?>> categoryConfigurables = LibrGetter.configManager.getConfigurablesForCategory(category);
            int categoryPagesCount = (int) Math.ceil(categoryConfigurables.size() / ((float) CONFIGS_PER_PAGE));
            for (int categoryPageIndex = 0; categoryPageIndex < categoryPagesCount; categoryPageIndex++) {
                int configSliceStart = categoryPageIndex * CONFIGS_PER_PAGE;
                int configSliceEnd = Math.min(configSliceStart + CONFIGS_PER_PAGE, categoryConfigurables.size());
                List<Configurable<?>> configurablesOnPage = categoryConfigurables.subList(configSliceStart, configSliceEnd);
                pageList.add(new Page(category, configurablesOnPage));
            }
            pageNumber += categoryPagesCount;
        }
        pageCount = pageNumber;
        pages = List.copyOf(pageList);
        categoryPageIndices = Map.copyOf(pageIndices);

        // pre-fill pages with empty text
        cachedPageTexts = new Component[pageCount];
        Arrays.fill(cachedPageTexts, Component.nullToEmpty(""));
    }

    public static BookViewScreen$BookAccess createNewBookAccess() {
        for (int pageIndex = 0; pageIndex < pageCount; pageIndex++) {
            updatePageCache(pageIndex);
        }
        return Gui.createBookAccess(Arrays.asList(cachedPageTexts));
    }

    public static BookViewScreen$BookAccess getUpdatedBookAccess(int updatePageIndex) {
        updatePageCache(updatePageIndex);
        return Gui.createBookAccess(Arrays.asList(cachedPageTexts));
    }

    private static void updatePageCache(int pageIndex) {
        if (pageIndex == 0) {
            cachedPageTexts[0] = Texts.bookMainPage(categoryPageIndices);
            return;
        }
        if (pageIndex < 0 || pageIndex >= pageCount) {
            throw new InvalidBookIndexException(pageIndex);
        }
        cachedPageTexts[pageIndex] = pages.get(pageIndex).buildText();
    }

    private record Page(ConfigManager.Category category, List<Configurable<?>> configurables) {
        public MutableComponent buildText() {
            MutableComponent text = Texts.bookTitle(category);
            for (Configurable<?> configurable : configurables) {
                text = Texts.bookEntry(text, configurable);
            }
            return text;
        }
    }
}
