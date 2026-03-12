package dev.gxlg.librgetter.gui;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.chaining.gui.Gui;
import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.config.ConfigManager;
import dev.gxlg.librgetter.utils.types.config.helpers.Configurable;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.screens.inventory.BookViewScreen$BookAccess;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Component;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.MutableComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigMenu {
    public static final int CONFIGS_PER_PAGE = 4;

    public static final int pageCount;

    private static final List<Component> list = new ArrayList<>();

    private static final Map<ConfigManager.Category, Integer> categoryPageIndices = new HashMap<>();

    static {
        int pageIndex = 1;
        for (ConfigManager.Category category : ConfigManager.Category.values()) {
            categoryPageIndices.put(category, pageIndex);
            pageIndex += (int) Math.ceil(LibrGetter.configManager.getConfigurablesForCategory(category).size() / ((float) CONFIGS_PER_PAGE));
        }
        pageCount = pageIndex;

        // pre-fill pages
        for (int i = 0; i < pageCount; i++) {
            list.add(Component.nullToEmpty(""));
        }
    }

    public static BookViewScreen$BookAccess createNewBookAccess() {
        for (int i = 0; i < pageCount; i++) {
            updatePageContent(i);
        }
        return Gui.createBookAccess(list);
    }

    public static BookViewScreen$BookAccess getUpdatedBookAccess(int updatePageIndex) {
        updatePageContent(updatePageIndex);
        return Gui.createBookAccess(list);
    }

    private static void updatePageContent(int index) {
        MutableComponent text;
        if (index == 0) {
            text = Texts.bookMainPage(categoryPageIndices);

        } else {
            List<ConfigManager.Category> categories = List.of(ConfigManager.Category.values());
            ConfigManager.Category category = categories.reversed().stream().filter(c -> categoryPageIndices.get(c) <= index).findFirst()
                                                        .orElseThrow(() -> new RuntimeException("Invalid index " + index));
            text = Texts.bookTitle(category);

            int startingPage = index - categoryPageIndices.get(category);
            int firstConfigIndex = startingPage * CONFIGS_PER_PAGE;
            int lastConfigIndex = Math.min(startingPage * CONFIGS_PER_PAGE + CONFIGS_PER_PAGE, LibrGetter.configManager.getConfigurablesForCategory(category).size());
            for (int configIndex = firstConfigIndex; configIndex < lastConfigIndex; configIndex++) {
                Configurable<?> config = LibrGetter.configManager.getConfigurablesForCategory(category).get(configIndex);
                text = Texts.bookEntry(text, config);
            }
        }
        list.set(index, text);
    }
}
